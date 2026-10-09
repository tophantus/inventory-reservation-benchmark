import http from 'k6/http';
import { check, fail } from 'k6';
import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/3.0.4/dist/bundle.js';

import { config } from './config.js';

export const options = {
  vus: config.vus,
  duration: config.duration,
  thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<1000'],
  },
};

const jsonParams = {
  headers: { 'Content-Type': 'application/json' },
};

function post(path, payload) {
  return http.post(`${config.baseUrl}${path}`, JSON.stringify(payload), jsonParams);
}

function requireCreated(response, resource) {
  const valid = check(response, {
    [`${resource} is created`]: (res) => res.status === 201,
    [`${resource} response is successful`]: (res) => res.json('success') === true,
  });

  if (!valid) {
    fail(`Could not create ${resource}: ${response.status} ${response.body}`);
  }

  return response.json('data');
}

export function setup() {
  const runId = Date.now();
  const customerIds = [];

  for (let index = 0; index < config.customerCount; index += 1) {
    const customer = requireCreated(
      post('/v1/customers', { name: `benchmark-customer-${runId}-${index}` }),
      'customer',
    );
    customerIds.push(customer.customerId);
  }

  const shop = requireCreated(
    post('/v1/shops', { name: `benchmark-shop-${runId}` }),
    'shop',
  );

  const product = requireCreated(
    post('/v1/products', {
      shopId: shop.shopId,
      name: `benchmark-product-${runId}`,
      sku: `benchmark-sku-${runId}`,
      price: 1.0,
      quantity: config.productQuantity,
    }),
    'product',
  );

  if (config.strategy === 'REDIS') {
    const preload = post(`/v1/reservations/redis-inventory/${product.productId}/preload`, null);
    const preloaded = check(preload, {
      'Redis inventory is preloaded': (res) => res.status === 200 && res.json('success') === true,
    });
    if (!preloaded) {
      fail(`Could not preload Redis inventory: ${preload.status} ${preload.body}`);
    }
  }

  return { customerIds, productId: product.productId };
}

export default function (data) {
  const customerId = data.customerIds[(__VU - 1) % data.customerIds.length];
  const response = post('/v1/reservations', {
    customerId,
    productId: data.productId,
    quantity: config.reservationQuantity,
    strategy: config.strategy,
  });

  check(response, {
    'reservation is created': (res) => res.status === 201,
    'reservation response is successful': (res) => res.json('success') === true,
    'reservation ID is returned': (res) => {
      const reservationId = res.json('data.reservationId');
      return typeof reservationId === 'number' && reservationId > 0;
    },
  });
}

export function handleSummary(data) {
  const now = new Date();
  const timestamp = [
    now.getUTCFullYear(),
    String(now.getUTCMonth() + 1).padStart(2, '0'),
    String(now.getUTCDate()).padStart(2, '0'),
  ].join('') + [
    String(now.getUTCHours()).padStart(2, '0'),
    String(now.getUTCMinutes()).padStart(2, '0'),
    String(now.getUTCSeconds()).padStart(2, '0'),
  ].join('');

  return {
    [`/reports/reservation-${config.strategy}-${timestamp}.html`]: htmlReport(data, {
      title: `Reservation benchmark: ${config.strategy}`,
    }),
  };
}

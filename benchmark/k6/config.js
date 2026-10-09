const strategies = {
  1: 'POSTGRES_PESSIMISTIC',
  2: 'REDIS',
  3: 'POSTGRES_POOL',
};

const strategyOption = 3;

export const config = {
  baseUrl: 'http://inventory-reservation-benchmark:8080',
  strategy: strategies[strategyOption],
  customerCount: 500,
  productQuantity: 1_000_000,
  reservationQuantity: 1,
  vus: 500,
  duration: '3m',
};

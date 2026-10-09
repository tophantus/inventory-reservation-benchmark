const strategies = {
  1: 'POSTGRES_PESSIMISTIC',
  2: 'REDIS',
  3: 'POSTGRES_POOL',
};

// Choose one strategy: 1 = pessimistic lock, 2 = Redis, 3 = PostgreSQL pool.
const strategyOption = 3;

export const config = {
  baseUrl: 'http://inventory-reservation-benchmark:8080',
  strategy: strategies[strategyOption],
  customerCount: 100,
  productQuantity: 100_000,
  reservationQuantity: 1,
  vus: 100,
  duration: '30s',
};

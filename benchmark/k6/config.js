export const config = {
  baseUrl: 'http://inventory-reservation-benchmark:8080',
  strategy: 'POSTGRES_PESSIMISTIC',
  customerCount: 100,
  productQuantity: 1000,
  reservationQuantity: 1,
  vus: 100,
  duration: '30s',
};

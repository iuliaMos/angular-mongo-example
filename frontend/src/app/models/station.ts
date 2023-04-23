export interface Station {
  nr?: number;
  externalId: string;
  nameFi: string;
  nameSe?: string;
  nameEn: string;
  addressFi: string;
  addressSe?: string;
  cityFi?: string;
  citySe?: string;
  operator?: string;
  capacities?: number;
  x: number;
  y: number;
}

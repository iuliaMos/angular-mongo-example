export interface Journey {
  departureTime: Date;
  returnTime: Date;
  departureStationId?: string;
  departureStationName: string;
  returnStationId?: string;
  returnStationName: string;
  distance: number;
  duration: number;
}

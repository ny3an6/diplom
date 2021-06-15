-- insert into base_station_info(cell_id, MCC, MNC, LAC, BER, SNR, RSSI, CH, neighbour) values (1,1,1,1,1,1,1,1,'2');
select * from base_station_info;

CREATE OR REPLACE FUNCTION public.measured_insert()
  RETURNS trigger AS
$BODY$BEGIN
  PERFORM pg_notify(
	'measured_current_cell_info_insert',
	json_build_object('ber', NEW.ber, 'ch', NEW.ch, 'cell_id', NEW.cell_id, 'lac', NEW.lac, 'mcc', NEW.mcc, 'mnc', NEW.mnc, 'rssi', NEW.rssi, 'snr', NEW.snr, 'scan_date', NEW.scan_date) :: text);
  RETURN NEW;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.measured_insert()
  OWNER TO postgres;



CREATE TRIGGER public_measured_insert
  AFTER INSERT
  ON public.base_station_info
  FOR EACH ROW
  EXECUTE PROCEDURE public.measured_insert();


CREATE OR REPLACE FUNCTION public.measured_insert_neighbors_info()
  RETURNS trigger AS
$BODY$BEGIN
  PERFORM pg_notify(
	'measured_neighbors_cell_info_insert',
	json_build_object('ch', NEW.ch,'lac', NEW.lac,'rssi', NEW.rssi,'scan_date', NEW.scan_date, 'cell_id', NEW.cell_id, 'cell_name', NEW.cell_name) :: text);
  RETURN NEW;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.measured_insert_neighbors_info()
  OWNER TO postgres;

CREATE TRIGGER public_measured_insert_neigbors
  AFTER INSERT
  ON public.neighbors_info
  FOR EACH ROW
  EXECUTE PROCEDURE public.measured_insert_neighbors_info();
--create index idx_vattu_sl_gm on
--vattu(soluongton,giamua)

 SET STATISTICS TIME ON;
 SET STATISTICS IO ON;
 DBCC DROPCLEANBUFFERS;
select * from VATTU with (index(idx_vattu_sl_gm)) 
where SoLuongTon > 100
and GiaMua >1000
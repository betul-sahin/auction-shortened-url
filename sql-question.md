Maaş tablosundaki en yüksek 5. maaşı bulunuz ?

```
  SELECT * from ((SELECT * from maas_tablosu 
  ORDER BY `maas` DESC limit 5 ) AS T) 
  ORDER BY T.`maas` ASC limit 1;
```

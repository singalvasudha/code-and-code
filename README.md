This code is in response to the YAKShop problem. Following urls can be used for testing the functionality:

YAK-1
http://localhost:8080/YakShop1/yak-shop/adminform GET

YAK-2
http://localhost:8080/YakShop1/yak-shop/stock/14 GET
http://localhost:8080/YakShop1/yak-shop/herd/14 GET

YAK-3
http://localhost:8080/YakShop1/yak-shop/order/13 POST

YAK-4
http://localhost:8080/YakShop1/yak-shop/placeorder GET

There is 1 known issue that the format of data sent & received in YAK-3 exercise is String, in place of JSON that was mentioned in the requirement of Yak-3.

Assumptions during coding:
1) Yak-1 will save data read from XML into database. The database script "script.sql" is checked in as part of the code for MySQL.
2) All order requests are independent, hence the application does not subtract the earlier ordered milk/skins amount from present day's stock.

more data.txt > bigdata.txt
FOR /L %%i IN (1,1,20) DO (
  more bigdata.txt >> bigdata.txt
)

docker run -p 8086:8086 -v /home/pi/volumes/influxdb:/var/lib/influxdb -e INFLUXDB_DB=home -e INFLUXDB_USER=spring -e INFLUXDB_USER_PASSWORD=spring --name influx_spring influxdb /init-influxdb.sh

docker run -p 8080:8080 localhost:5000/home-application

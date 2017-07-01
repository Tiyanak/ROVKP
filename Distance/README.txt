UPUTE ZA POKRETANJE PRIMJERA

//U svojoj korisnièkoj mapi (tj. u mapi ~ na Linuxu) stvorite podmapu ROVKP_DZ2 i postavite je kao radnu
//Sve daljnje korake izvoditi æete iz ove radne mape
//Datoteku s voznjama taksija kopirati u ulazni direktorij npr. ROVKP_DZ2/input
//Distance-1.0-SNAPSHOT.jar je arhiva primjera s predavanja, u direktoriju Distance/target/
//Distance-1.0-SNAPSHOT.jar je potrebno kopirati u radni direktorij 

export JAVA_HOME=/usr/lib/jvm/default
export HADOOP_HOME=/opt/hadoop-2.6.4
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
start-dfs.sh
start-yarn.sh 

//Prije pokretanja primjera provjeriti sadrzaj izlaznog direktorija na HDFS-u
//npr. hdfs dfs -ls output, ako ga treba brisati koristite hdfs dfs -rm -r output

Pokretanje primjera za Total Distance (predavanje 1):
hadoop jar Distance-1.0-SNAPSHOT.jar hr.fer.tel.rovkp.topic1.example2.TotalDistance file:/home/rovkp/input output

Pokretanje primjera za Average Distance (predavanje 4):
hadoop jar Distance-1.0-SNAPSHOT.jar hr.fer.tel.rovkp.topic4.example1.AverageDistance file:/home/rovkp/input output

Pokretanje primjera za WeekDayWithCounters (predavanje 4):
hadoop jar Distance-1.0-SNAPSHOT.jar hr.fer.tel.rovkp.topic4.example2.WeekDayWithCounters file:/home/rovkp/input output

Pokretanje primjera za WeekDayPartitionerExample (predavanje 4):
hadoop jar Distance-1.0-SNAPSHOT.jar hr.fer.tel.rovkp.topic4.example3.WeekDayPartitionerExample file:/home/rovkp/input output

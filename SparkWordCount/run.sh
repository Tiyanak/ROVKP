 #!/bin/bash
/opt/spark-1.5.0-bin-hadoop2.6/bin/spark-submit --master local target/SparkWordCount-1.0-SNAPSHOT.jar /home/kpripuzic/JavaProjects/HadoopProjects/sherlock_holmes.txt output

 #!/bin/bash
/opt/spark-1.5.0-bin-hadoop2.6/bin/spark-submit --master local target/SparkTotalDistance-1.0-SNAPSHOT.jar /home/kpripuzic/Magnetic/debs2015/tinyinput/sorted_data.csv output

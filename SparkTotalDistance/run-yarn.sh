 #!/bin/bash
/opt/spark-1.5.0-bin-hadoop2.6/bin/spark-submit --master yarn --deploy-mode cluster target/SparkTotalDistance-1.0-SNAPSHOT.jar hdfs://localhost:9000/user/kpripuzic/sorted_data.csv hdfs://localhost:9000/user/kpripuzic/output

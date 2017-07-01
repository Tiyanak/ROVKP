 #!/bin/bash
cat /home/kpripuzic/Magnetic/debs2015/tinyinput/sorted_data.csv | nc -l -i 1 -p 18181 localhost

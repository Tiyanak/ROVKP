 #!/bin/bash
cat ../sherlock_holmes.txt | nc -l -i 1 -p 18181 localhost

-- loading java class as function from classpath

local jtest = require('jtest')

assert(jtest() == true)
print('jtest', jtest())
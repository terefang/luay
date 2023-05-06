local _http = require('http')

local _resp = _http.get('https://www.google.com')


_resp = _http.new():get('https://www.google.com')

for _,x in ipairs(_resp['cookies']) do
    print('Cookie:',x)
end

for k,v in pairs(_resp['header']) do
    print('Header:',k,'=',v)
end

print(_resp['body'])

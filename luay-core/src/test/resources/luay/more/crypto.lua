-- https://hashcat.net/wiki/doku.php?id=example_hashes

local _hash = require('hash')
local _cy = require('crypto')

local _pads = mklist(
        --'NoPadding'
        --'ISO10126d2Padding',
        --'X932Padding',
        --'ISO7816d4Padding',
        'ZeroBytePadding',
        'TBCPadding',
        'PKCS7Padding',
        'PKCS5Padding'
);

local _list = mklist(   'Artpler',  'Besburobphette',   'Boshogahn',    'Dennis',   'Fania',
                        'Fenmichha','Gao',  'Hamomonico',   'Hieronymus Gundry',    'Jolanda Claes',
                        'Malu', 'Manno Baanders',   'Melyor Joossens',  'Michdea',  'Mick Lowry',
                        'Moos', 'Morcum Maes',  'Nuteenico',    'Rette',    'Rosanne Dirkx',
                        'Sidwella Goossens',    'Solarria', 'Tocigram', 'Traia',    'Tuegarposos',
                        'Uld');

print('---','encrypt');
local _bf = _cy.blowfish_cbc_pkcs5(true, 'deadbeef', '12345678') --
for _,str in ipairs(_list) do
    print(str,_hash.to_hex(_bf(str)));
end
print('final:',_hash.to_hex(_bf()));

for _,_pad in ipairs(_pads) do
    print('---','cypher', _pad);
    _bf = _cy.cipher('Blowfish/CBC/'.._pad, true, 'deadbeef', '12345678')--
    for _,str in ipairs(_list) do
        local _b = _bf(str)
        --print(str,_hash.to_hex(_b));
    end
    print('final:',_hash.to_hex(_bf()));
end

_list = mklist( '3C38BCEF0FBBEFD0', '4620C29202CE13D1', '0D89CFF89C0C64AE', 'DC49C2046313A498',
                '6DBCA0F39D4553F0', '7A46EE93170FA040', '06CE12364DD44736', '5EFF68A05D1DD1FE',
                'DA368320DA017496', '7F11740DC0E676BE', '6ECCC3085B0DFCDB', '7D0A7F74991A7496',
                '754AF3B266AEB06E', 'A1A10209986203B4', '3A771BB7910A8CC3', 'B0A4446EC429B11A',
                'F4C3531324E315E7', '1604C80DAFA92E35', '00B356D7A5CE48B4', '9BDFEAE24A9B88D2',
                'F59FC9B6D8E172F8', '08DCA697969B6E6E', 'B08A792179AD1B30', 'F29209E11741D29D',
                '1D8E8115DF0FE919', '29DA97114B50530E', 'C8BB9C5F36C92474', '8D4BA37D1739AE52',
                '134E80F2D432719E', '1886D54607CDF7FA' );

print('---','decrypt');
_bf = _cy.blowfish_cbc_pkcs5(false, 'deadbeef', '12345678')--
for _,str in ipairs(_list) do
    local _b = _hash.from_hex(str)
    local _p = _bf(_b)
    print(str,_p);
end
print('final:',_bf());


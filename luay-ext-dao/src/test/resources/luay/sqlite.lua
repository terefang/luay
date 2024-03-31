local _sql = package.loadlib('sql')

local _conn = _sql.sqlite('./test.db', 'sa', '');

_conn:autocommit(true);

_res, _ = _conn:execute('drop table if exists tbl ');
print('res=', stringify(_res),_);

_res, _ = _conn:execute('create table tbl (id, name)');
print('res=', stringify(_res),_);

_res, _ = _conn:insert('insert into tbl values (?, ?)', '1', 'one');
print('res=', stringify(_res),_);

_res, _ = _conn:insert('insert into tbl values (?, ?)', '2', 'two');
print('res=', stringify(_res),_);

_res, _ = _conn:query('select * from tbl');
print('res=', stringify(_res),_);
for _,_row in ipairs(_res) do
    print(_)
end

_res, _ = _conn:query('select * from tbl where id = ?{id}', mkmap('id', 1));
print('res=', stringify(_res),_);
for _,_row in ipairs(_res) do
    print(_)
end

_res, _ = _conn:query('select * from tbl where id = ?', 1);
print('res=', stringify(_res),_);
for _,_row in ipairs(_res) do
    print(_)
end

_conn:close();

local _list = _sql.drivers();

for _, n in ipairs(_list) do
    print(n)
end

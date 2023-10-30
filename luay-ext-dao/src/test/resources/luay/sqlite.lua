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


_conn:close();

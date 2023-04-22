package luay.lib.ext;

import com.github.terefang.jmelange.commons.http.HttpClientResponse;
import com.github.terefang.jmelange.commons.http.HttpOkClient;
import lombok.SneakyThrows;
import luay.vm.*;
import luay.vm.lib.java.CoerceJavaToLua;
import luay.vm.luay.AbstractLibrary;

import java.util.HashMap;
import java.util.List;

public class OkHttpLib extends AbstractLibrary
{
    @Override
    public String getLibraryName() {
        return "http";
    }

    static _okhttpclient INSTANCE = new _okhttpclient();
    @Override
    public List<LuaFunction> getLibraryFunctions() {
        return AbstractLibrary.toList(
                _varArgFunctionWrapper.from("get", OkHttpLib::get),
                _varArgFunctionWrapper.from("post", OkHttpLib::post),
                _varArgFunctionWrapper.from("put", OkHttpLib::put),
                _varArgFunctionWrapper.from("delete", OkHttpLib::delete),
                _varArgFunctionWrapper.from("new", OkHttpLib::new_)
        );
    }

    public static Varargs get(Varargs args)
    {
        return INSTANCE.get(args);
    }

    public static Varargs post(Varargs args)
    {
        return INSTANCE.post(args);
    }

    public static Varargs put(Varargs args)
    {
        return INSTANCE.put(args);
    }

    public static Varargs delete(Varargs args)
    {
        return INSTANCE.delete(args);
    }

    @SneakyThrows
    public static Varargs new_(Varargs args)
    {
        return CoerceJavaToLua.coerce(new _okhttpclient());
    }

    public static class _okhttpresponse
    {
        public int status;
        public String content_type;
        public byte[] body;
        public String message;
        public LuaList cookies;
        public LuaTable header;

        @SneakyThrows
        public static _okhttpresponse from(HttpClientResponse _resp)
        {
            _okhttpresponse _r = new _okhttpresponse();
            _r.status = _resp.getStatus();
            _r.content_type = _resp.getContentType();
            _r.message = _resp.getMessage();
            _r.body = _resp.getAsBytes();
            _r.cookies = new LuaList();
            _resp.getCookieJar().forEach((x)->{ _r.cookies.insert(LuaString.valueOf((String)x));});
            _r.header = new LuaTable();
            _resp.getHeader().forEach((a,b)->{ _r.header.set((String)a,LuaString.valueOf((String)b));});
            return _r;
        }
    }

    public static class _okhttpclient
    {
        HttpOkClient _client = new HttpOkClient();
        public LuaValue proxy(Varargs args)
        {
            if(args.narg()==0)
            {
                return LuaString.valueOf(_client.getProxyUrl());
            }
            else
            {
                _client.setProxyUrl(args.checkjstring(1));
            }
            return LuaValue.NONE;
        }

        public LuaValue accept_encoding(Varargs args)
        {
            if(args.narg()==0)
            {
                return LuaString.valueOf(_client.getAcceptEncoding());
            }
            else
            {
                _client.setAcceptEncoding(args.checkjstring(1));
            }
            return LuaValue.NONE;
        }

        public LuaValue accept_type(Varargs args)
        {
            if(args.narg()==0)
            {
                return LuaString.valueOf(_client.getAcceptType());
            }
            else
            {
                _client.setAcceptType(args.checkjstring(1));
            }
            return LuaValue.NONE;
        }

        public LuaValue content_type(Varargs args)
        {
            if(args.narg()==0)
            {
                return LuaString.valueOf(_client.getContentType());
            }
            else
            {
                _client.setContentType(args.checkjstring(1));
            }
            return LuaValue.NONE;
        }

        public LuaValue ssl_version(Varargs args)
        {
            if(args.narg()==0)
            {
                return LuaString.valueOf(_client.getSslVersion());
            }
            else
            {
                _client.setSslVersion(args.checkjstring(1));
            }
            return LuaValue.NONE;
        }

        // timeout(int)
        public LuaValue timeout(Varargs args)
        {
            if(args.narg()==1)
            {
                _client.setTimeout(args.checkint(1));
            }
            return LuaValue.NONE;
        }

        // follow(bool)
        public LuaValue follow(Varargs args)
        {
            if(args.narg()==1)
            {
                _client.setFollowRedirects(args.checkboolean(1));
            }
            return LuaValue.NONE;
        }

        // header(key) -> value
        // header(key, value [, keyN, valueN])
        // header(table)
        public LuaValue header(Varargs args)
        {
            if(args.narg()==1 && args.isstring(1))
            {
                return LuaString.valueOf(_client.getRequestHeader().get(args.checkstring(1)));
            }
            else
            if(args.narg()==1 && args.istable(1))
            {
                LuaTable _table = args.checktable(1);
                for(LuaValue _k : _table.keys())
                {
                    _client.addRequestHeader(_k.checkjstring(), _table.get(_k).checkjstring());
                }
            }
            else
            if(args.narg()%2==0)
            {
                for(int _i=0; _i<args.narg(); _i+=2)
                {
                    _client.addRequestHeader(args.checkjstring(_i+1), args.checkjstring(_i+2));
                }
            }
            return LuaValue.NONE;
        }

        // login(user)
        // login(user,pass)
        public LuaValue login(Varargs args)
        {
            if(args.narg()==1 && args.isstring(1))
            {
                _client.setLoginUsername(args.checkjstring(1));
            }
            else
            if(args.narg()==2 && args.isstring(1) && args.isstring(2))
            {
                _client.setLoginCredential(args.checkjstring(1), args.checkjstring(2));
            }
            else
            {
                LuaValue.error("illegal arguments");
            }
            return LuaValue.NONE;
        }

        // get(url, accept)
        // get(url)
        @SneakyThrows
        public Varargs get(Varargs args)
        {
            HttpClientResponse _resp = null;
            if(args.narg()==2)
            {
                _resp = _client.getRequest(args.checkjstring(1), args.checkjstring(2));
            }
            else
            if(args.narg()==1)
            {
                _resp = _client.getRequest(args.checkjstring(1));
            }
            else
            {
                return LuaValue.varargsOf(LuaValue.NIL, LuaString.valueOf("illegal arguments"));
            }

            return CoerceJavaToLua.coerce(_okhttpresponse.from(_resp));
        }

        // post(url, content, accept, data)
        // post(url, accept, query)
        // post(url, content, data)
        // post(url, query)
        @SneakyThrows
        public Varargs post(Varargs args)
        {
            HttpClientResponse _resp = null;
            String _method = "POST";
            String _url;
            String _contentType = this.content_type(LuaValue.NONE).checkjstring();
            String _acceptType = this.accept_type(LuaValue.NONE).checkjstring();
            byte[] _data;
            if(args.narg()==4)
            {
                _url = args.checkjstring(1);
                _contentType = args.checkjstring(2);
                _acceptType = args.checkjstring(3);
                _data = args.checkstring(4).getBytes();
                _resp = _client.executeRequest(_url, _method, _contentType, _acceptType, _data);
            }
            else
            if(args.narg()==3)
            {
                _url = args.checkjstring(1);
                _acceptType = args.checkjstring(2);
                if(args.istable(3))
                {
                    LuaTable _table = args.checktable(3);
                    HashMap<String, String> _form = new HashMap<>();
                    for(LuaValue _k : _table.keys())
                    {
                        _form.put(_k.checkjstring(), _table.get(_k).checkjstring());
                    }
                    _resp = _client.postForm(_url, _acceptType, _form);
                }
                else
                {
                    _data = args.checkstring(3).getBytes();
                    _resp = _client.postRequest(_url, _contentType, _data);
                }
            }
            else
            if(args.narg()==2)
            {
                _url = args.checkjstring(1);
                LuaTable _table = args.checktable(2);
                HashMap<String, String> _form = new HashMap<>();
                for(LuaValue _k : _table.keys())
                {
                    _form.put(_k.checkjstring(), _table.get(_k).checkjstring());
                }
                _resp = _client.postForm(_url, "*/*", _form);
            }
            else
            {
                return LuaValue.varargsOf(LuaValue.NIL, LuaString.valueOf("illegal arguments"));
            }
            return CoerceJavaToLua.coerce(_okhttpresponse.from(_resp));
        }

        // put(url, content, accept, data)
        // put(url, content, data)
        @SneakyThrows
        public Varargs put(Varargs args)
        {
            HttpClientResponse _resp = null;
            String _url;
            String _contentType = this.content_type(LuaValue.NONE).checkjstring();
            String _acceptType = this.accept_type(LuaValue.NONE).checkjstring();
            byte[] _data;
            if(args.narg()==4)
            {
                _url = args.checkjstring(1);
                _contentType = args.checkjstring(2);
                _acceptType = args.checkjstring(3);
                _data = args.checkstring(4).getBytes();
                _resp = _client.putRequest(_url, _contentType, _data);
            }
            else
            if(args.narg()==3)
            {
                _url = args.checkjstring(1);
                _contentType = args.checkjstring(2);
                _data = args.checkstring(3).getBytes();
                _resp = _client.putRequest(_url, _contentType, _data);
            }
            else
            {
                return LuaValue.varargsOf(LuaValue.NIL, LuaString.valueOf("illegal arguments"));
            }
            return CoerceJavaToLua.coerce(_okhttpresponse.from(_resp));
        }

        // delete(url)
        @SneakyThrows
        public Varargs delete(Varargs args)
        {
            HttpClientResponse _resp = null;
            String _url;
            if(args.narg()==1)
            {
                _url = args.checkjstring(1);
                _resp = _client.deleteRequest(_url);
            }
            else
            {
                return LuaValue.varargsOf(LuaValue.NIL, LuaString.valueOf("illegal arguments"));
            }
            return CoerceJavaToLua.coerce(_okhttpresponse.from(_resp));
        }

        // execute({ method = , url = , content = , accept = , data = })
        // execute(method, url, content, accept, data)
        // execute(method, url, content, data)
        // execute(method, url, data)
        @SneakyThrows
        public Varargs execute(Varargs args)
        {
            HttpClientResponse _resp = null;
            String _method = null;
            String _url = null;
            String _contentType = this.content_type(LuaValue.NONE).checkjstring();
            String _acceptType = this.accept_type(LuaValue.NONE).checkjstring();
            byte[] _data = null;
            // param form
            if(args.narg()==1 && args.istable(1))
            {
                LuaTable _table = args.checktable(1);
            }
            // positional forms
            else
            if(args.narg()==5)
            {
                _method = args.checkjstring(1);
                _url = args.checkjstring(2);
                _contentType = args.checkjstring(3);
                _acceptType = args.checkjstring(4);
                _data = args.checkstring(5).getBytes();
            }
            else
            if(args.narg()==4)
            {
                _method = args.checkjstring(1);
                _url = args.checkjstring(2);
                _contentType = args.checkjstring(3);
                _data = args.checkstring(4).getBytes();
            }
            else
            if(args.narg()==3)
            {
                _method = args.checkjstring(1);
                _url = args.checkjstring(2);
                _data = args.checkstring(3).getBytes();
            }
            else
            {
                return LuaValue.varargsOf(LuaValue.NIL, LuaString.valueOf("illegal arguments"));
            }

            _resp = _client.executeRequest(_method, _url, _contentType, _acceptType, _data);
            return CoerceJavaToLua.coerce(_okhttpresponse.from(_resp));
        }
    }
}

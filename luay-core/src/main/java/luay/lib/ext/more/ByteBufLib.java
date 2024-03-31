package luay.lib.ext.more;

import lombok.SneakyThrows;
import luay.lib.LuayLibraryFactory;
import luay.lib.ext.AbstractLibrary;
import luay.vm.LuaFunction;
import luay.vm.LuaString;
import luay.vm.LuaValue;
import luay.vm.Varargs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class ByteBufLib extends AbstractLibrary implements LuayLibraryFactory
{
    @Override
    public String getLibraryName() {
        return "bytebuf";
    }

    @Override
    public List<LuaFunction> getLibraryFunctions() {
        return toList(
                _varArgFunctionWrapper.from("alloc", ByteBufLib::_alloc),
                _varArgFunctionWrapper.from("orderbe", ByteBufLib::_orderbe),
                _varArgFunctionWrapper.from("orderle", ByteBufLib::_orderle),
                _varArgFunctionWrapper.from("clear", ByteBufLib::_clear),
                _varArgFunctionWrapper.from("flip", ByteBufLib::_flip),
                _varArgFunctionWrapper.from("compact", ByteBufLib::_compact),
                _varArgFunctionWrapper.from("reset", ByteBufLib::_reset),
                _varArgFunctionWrapper.from("rewind", ByteBufLib::_rewind),
                _varArgFunctionWrapper.from("capacity", ByteBufLib::_capacity),
                _varArgFunctionWrapper.from("position", ByteBufLib::_position),
                _varArgFunctionWrapper.from("mark", ByteBufLib::_mark),
                _varArgFunctionWrapper.from("limit", ByteBufLib::_limit),
                _varArgFunctionWrapper.from("remaining", ByteBufLib::_remaining),
                _varArgFunctionWrapper.from("hasremaining", ByteBufLib::_hasremaining),
                _varArgFunctionWrapper.from("duplicate", ByteBufLib::_duplicate),
                _varArgFunctionWrapper.from("slice", ByteBufLib::_slice),
                _varArgFunctionWrapper.from("write", ByteBufLib::_write),
                _varArgFunctionWrapper.from("readbyte", ByteBufLib::_readbyte),
                _varArgFunctionWrapper.from("writebyte", ByteBufLib::_writebyte),
                _varArgFunctionWrapper.from("setbyte", ByteBufLib::_setbyte),
                _varArgFunctionWrapper.from("getbyte", ByteBufLib::_getbyte),
                _varArgFunctionWrapper.from("readbytes", ByteBufLib::_readbytes),
                _varArgFunctionWrapper.from("writebytes", ByteBufLib::_writebytes),
                _varArgFunctionWrapper.from("setbytes", ByteBufLib::_setbytes),
                _varArgFunctionWrapper.from("getbytes", ByteBufLib::_getbytes),
                _varArgFunctionWrapper.from("readshort", ByteBufLib::_readshort),
                _varArgFunctionWrapper.from("writeshort", ByteBufLib::_writeshort),
                _varArgFunctionWrapper.from("setshort", ByteBufLib::_setshort),
                _varArgFunctionWrapper.from("getshort", ByteBufLib::_getshort),
                _varArgFunctionWrapper.from("readint", ByteBufLib::_readint),
                _varArgFunctionWrapper.from("writeint", ByteBufLib::_writeint),
                _varArgFunctionWrapper.from("setint", ByteBufLib::_setint),
                _varArgFunctionWrapper.from("getint", ByteBufLib::_getint),
                _varArgFunctionWrapper.from("readchar", ByteBufLib::_readchar),
                _varArgFunctionWrapper.from("writechar", ByteBufLib::_writechar),
                _varArgFunctionWrapper.from("setchar", ByteBufLib::_setchar),
                _varArgFunctionWrapper.from("getchar", ByteBufLib::_getchar),
                _varArgFunctionWrapper.from("readlong", ByteBufLib::_readlong),
                _varArgFunctionWrapper.from("writelong", ByteBufLib::_writelong),
                _varArgFunctionWrapper.from("setlong", ByteBufLib::_setlong),
                _varArgFunctionWrapper.from("getlong", ByteBufLib::_getlong),
                _varArgFunctionWrapper.from("readfloat", ByteBufLib::_readfloat),
                _varArgFunctionWrapper.from("writefloat", ByteBufLib::_writefloat),
                _varArgFunctionWrapper.from("setfloat", ByteBufLib::_setfloat),
                _varArgFunctionWrapper.from("getfloat", ByteBufLib::_getfloat),
                _varArgFunctionWrapper.from("readdouble", ByteBufLib::_readdouble),
                _varArgFunctionWrapper.from("writedouble", ByteBufLib::_writedouble),
                _varArgFunctionWrapper.from("setdouble", ByteBufLib::_setdouble),
                _varArgFunctionWrapper.from("getdouble", ByteBufLib::_getdouble),
                _varArgFunctionWrapper.from("free", ByteBufLib::_free)
        );
    }

    // bytebuf.alloc(size[, direct]) -> bytebuf
    @SneakyThrows
    public static LuaValue _alloc(Varargs args) {
        int _size = args.checkint(1);
        boolean _direct = args.optboolean(5, false);

        return LuaValue.userdataOf(_direct ? ByteBuffer.allocateDirect(_size) : ByteBuffer.allocate(_size));
    }

    // bytebuf.free(bytebuf)
    @SneakyThrows
    public static LuaValue _free(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        _buf.clear();
        return LuaValue.NONE;
    }

    // bytebuf.duplicate(bytebuf) -> bytebuf
    @SneakyThrows
    public static LuaValue _duplicate(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.userdataOf(_buf.duplicate());
    }

    // bytebuf.slice(bytebuf) -> bytebuf
    @SneakyThrows
    public static LuaValue _slice(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.userdataOf(_buf.slice());
    }

    // bytebuf.orderbe(bytebuf)
    @SneakyThrows
    public static LuaValue _orderbe(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        _buf.order(ByteOrder.BIG_ENDIAN);
        return LuaValue.NONE;
    }

    // bytebuf.orderle(bytebuf)
    @SneakyThrows
    public static LuaValue _orderle(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        _buf.order(ByteOrder.LITTLE_ENDIAN);
        return LuaValue.NONE;
    }

    // bytebuf.clear(bytebuf)
    @SneakyThrows
    public static LuaValue _clear(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        _buf.clear();
        return LuaValue.NONE;
    }

    // bytebuf.flip(bytebuf)
    @SneakyThrows
    public static LuaValue _flip(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        _buf.flip();
        return LuaValue.NONE;
    }

    // bytebuf.compact(bytebuf)
    @SneakyThrows
    public static LuaValue _compact(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        _buf.compact();
        return LuaValue.NONE;
    }

    // bytebuf.reset(bytebuf)
    @SneakyThrows
    public static LuaValue _reset(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        _buf.reset();
        return LuaValue.NONE;
    }

    // bytebuf.rewind(bytebuf)
    @SneakyThrows
    public static LuaValue _rewind(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        _buf.rewind();
        return LuaValue.NONE;
    }

    // bytebuf.mark(bytebuf)
    @SneakyThrows
    public static LuaValue _mark(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        _buf.mark();
        return LuaValue.NONE;
    }

    // bytebuf.capacity(bytebuf) -> int
    @SneakyThrows
    public static LuaValue _capacity(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.valueOf(_buf.capacity());
    }

    // bytebuf.remaining(bytebuf) -> int
    @SneakyThrows
    public static LuaValue _remaining(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.valueOf(_buf.remaining());
    }

    // bytebuf.hasremaining(bytebuf) -> bool
    @SneakyThrows
    public static LuaValue _hasremaining(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.valueOf(_buf.hasRemaining());
    }

    // bytebuf.limit(bytebuf[, newlimit]) -> int
    @SneakyThrows
    public static LuaValue _limit(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        if(args.narg()==2)
        {
            int _size = args.checkint(2);
            _buf.limit(_size);
        }
        return LuaValue.valueOf(_buf.limit());
    }

    // bytebuf.position(bytebuf[, newposition]) -> int
    @SneakyThrows
    public static LuaValue _position(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        if(args.narg()==2)
        {
            int _size = args.checkint(2);
            _buf.position(_size);
        }
        return LuaValue.valueOf(_buf.position());
    }



    // bytebuf.write(bytebuf, bytebuf)
    @SneakyThrows
    public static LuaValue _write(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        ByteBuffer _buf2 = (ByteBuffer) args.checkuserdata(2, ByteBuffer.class);
        _buf.put(_buf2);
        return LuaValue.NONE;
    }



    // bytebuf.readbytes(bytebuf[, size]) -> bytes
    @SneakyThrows
    public static LuaValue _readbytes(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        byte[] _out = null;
        if(args.narg()==2)
        {
            int _size = args.checkint(2);
            _out = new byte[_size];
        }
        else
        {
            _out = new byte[1];
        }
        _buf.get(_out);
        return LuaValue.valueOf(_out);
    }

    // bytebuf.getbytes(bytebuf, index[, size]) -> bytes
    @SneakyThrows
    public static LuaValue _getbytes(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        byte[] _out = null;
        if(args.narg()==3)
        {
            int _size = args.checkint(3);
            _out = new byte[_size];
        }
        else
        {
            _out = new byte[1];
        }

        for(int _i=0; _i<_out.length; _i++)
        {
            _out[_i]=_buf.get(_index+_i);
        }
        return LuaValue.valueOf(_out);
    }

    // bytebuf.writebytes(bytebuf, bytes)
    @SneakyThrows
    public static LuaValue _writebytes(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        LuaString _out = args.checkstring(2);
        _buf.put(_out.getBytes());
        return LuaValue.NONE;
    }

    // bytebuf.setbytes(bytebuf, index, bytes)
    @SneakyThrows
    public static LuaValue _setbytes(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        byte[] _out = args.checkstring(3).getBytes();
        for(int _i=0; _i<_out.length; _i++)
        {
            _buf.put(_index+_i, _out[_i]);
        }
        return LuaValue.NONE;
    }



    // bytebuf.readbyte(bytebuf) -> byte
    @SneakyThrows
    public static LuaValue _readbyte(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.valueOf((int)_buf.get());
    }

    // bytebuf.getbyte(bytebuf, index) -> byte
    @SneakyThrows
    public static LuaValue _getbyte(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        return LuaValue.valueOf((int)_buf.get(_index));
    }

    // bytebuf.writebyte(bytebuf, byte)
    @SneakyThrows
    public static LuaValue _writebyte(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _out = args.checkint(2);
        _buf.put((byte)(_out & 0xff));
        return LuaValue.NONE;
    }

    // bytebuf.setbyte(bytebuf, index, byte)
    @SneakyThrows
    public static LuaValue _setbyte(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        int _out = args.checkint(3);
        _buf.put(_index, (byte)(_out & 0xff));
        return LuaValue.NONE;
    }



    // bytebuf.readshort(bytebuf) -> short
    @SneakyThrows
    public static LuaValue _readshort(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.valueOf((int)_buf.getShort());
    }

    // bytebuf.getshort(bytebuf, index) -> short
    @SneakyThrows
    public static LuaValue _getshort(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        return LuaValue.valueOf((int)_buf.getShort(_index));
    }

    // bytebuf.writeshort(bytebuf, short)
    @SneakyThrows
    public static LuaValue _writeshort(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _out = args.checkint(2);
        _buf.putShort((short)(_out & 0xffff));
        return LuaValue.NONE;
    }

    // bytebuf.setshort(bytebuf, index, short)
    @SneakyThrows
    public static LuaValue _setshort(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        int _out = args.checkint(3);
        _buf.putShort(_index, (short)(_out & 0xffff));
        return LuaValue.NONE;
    }



    // bytebuf.readint(bytebuf) -> int
    @SneakyThrows
    public static LuaValue _readint(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.valueOf((int)_buf.getInt());
    }

    // bytebuf.getint(bytebuf, index) -> int
    @SneakyThrows
    public static LuaValue _getint(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        return LuaValue.valueOf((int)_buf.getInt(_index));
    }

    // bytebuf.writeint(bytebuf, int)
    @SneakyThrows
    public static LuaValue _writeint(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _out = args.checkint(2);
        _buf.putInt(_out);
        return LuaValue.NONE;
    }

    // bytebuf.setint(bytebuf, index, int)
    @SneakyThrows
    public static LuaValue _setint(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        int _out = args.checkint(3);
        _buf.putInt(_index, _out);
        return LuaValue.NONE;
    }



    // bytebuf.readlong(bytebuf) -> long
    @SneakyThrows
    public static LuaValue _readlong(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.valueOf(_buf.getLong());
    }

    // bytebuf.getlong(bytebuf, index) -> long
    @SneakyThrows
    public static LuaValue _getlong(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        return LuaValue.valueOf(_buf.getLong(_index));
    }

    // bytebuf.writelong(bytebuf, long)
    @SneakyThrows
    public static LuaValue _writelong(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        long _out = args.checklong(2);
        _buf.putLong(_out);
        return LuaValue.NONE;
    }

    // bytebuf.setlong(bytebuf, index, long)
    @SneakyThrows
    public static LuaValue _setlong(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        long _out = args.checklong(3);
        _buf.putLong(_index, _out);
        return LuaValue.NONE;
    }



    // bytebuf.readfloat(bytebuf) -> float
    @SneakyThrows
    public static LuaValue _readfloat(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.valueOf((double) _buf.getFloat());
    }

    // bytebuf.getfloat(bytebuf, index) -> float
    @SneakyThrows
    public static LuaValue _getfloat(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        return LuaValue.valueOf((double) _buf.getFloat(_index));
    }

    // bytebuf.writefloat(bytebuf, float)
    @SneakyThrows
    public static LuaValue _writefloat(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        float _out = (float) args.checkdouble(2);
        _buf.putFloat(_out);
        return LuaValue.NONE;
    }

    // bytebuf.setfloat(bytebuf, index, float)
    @SneakyThrows
    public static LuaValue _setfloat(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        float _out = (float) args.checkdouble(3);
        _buf.putFloat(_index, _out);
        return LuaValue.NONE;
    }



    // bytebuf.readdouble(bytebuf) -> double
    @SneakyThrows
    public static LuaValue _readdouble(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.valueOf(_buf.getDouble());
    }

    // bytebuf.getdouble(bytebuf, index) -> double
    @SneakyThrows
    public static LuaValue _getdouble(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        return LuaValue.valueOf(_buf.getDouble(_index));
    }

    // bytebuf.writedouble(bytebuf, double)
    @SneakyThrows
    public static LuaValue _writedouble(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        double _out = args.checkdouble(2);
        _buf.putDouble(_out);
        return LuaValue.NONE;
    }

    // bytebuf.setdouble(bytebuf, index, double)
    @SneakyThrows
    public static LuaValue _setdouble(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        double _out = args.checkdouble(3);
        _buf.putDouble(_index, _out);
        return LuaValue.NONE;
    }



    // bytebuf.readchar(bytebuf) -> int
    @SneakyThrows
    public static LuaValue _readchar(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        return LuaValue.valueOf((int)_buf.getChar());
    }

    // bytebuf.getchar(bytebuf, index) -> int
    @SneakyThrows
    public static LuaValue _getchar(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        return LuaValue.valueOf((int)_buf.getChar(_index));
    }

    // bytebuf.writechar(bytebuf, int)
    @SneakyThrows
    public static LuaValue _writechar(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _out = args.checkint(2);
        _buf.putChar((char) _out);
        return LuaValue.NONE;
    }

    // bytebuf.setchar(bytebuf, index, int)
    @SneakyThrows
    public static LuaValue _setchar(Varargs args) {
        ByteBuffer _buf = (ByteBuffer) args.checkuserdata(1, ByteBuffer.class);
        int _index = args.checkint(2);
        int _out = args.checkint(3);
        _buf.putChar(_index, (char) _out);
        return LuaValue.NONE;
    }
}

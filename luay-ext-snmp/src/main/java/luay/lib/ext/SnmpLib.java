package luay.lib.ext;

import com.github.terefang.jmelange.commons.util.GuidUtil;
import lombok.SneakyThrows;
import luay.lib.LuayLibraryFactory;
import luay.vm.*;
import org.opennms.protocols.snmp.*;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnmpLib extends AbstractLibrary implements LuayLibraryFactory
{
    @Override
    public String getLibraryName() {
        return "snmp";
    }

    @Override
    public String getName() {
        return "snmp";
    }

    @Override
    public List<LuaFunction> getLibraryFunctions() {
        return Arrays.asList(
                _varArgFunctionWrapper.from("snmpv2",SnmpLib::_createSnmpSession),
                _varArgFunctionWrapper.from("close",SnmpLib::_close),
                _varArgFunctionWrapper.from("closeall",SnmpLib::_closeAll),
                _varArgFunctionWrapper.from("get",SnmpLib::_get),
                _varArgFunctionWrapper.from("getall",SnmpLib::_getall),
                _varArgFunctionWrapper.from("walk",SnmpLib::_walk),
                _varArgFunctionWrapper.from("next",SnmpLib::_next)
        );
    }

    static Map<String,SnmpSession> _sessions = new HashMap<>();

    public static Varargs _close(Varargs _args)
    {
        String _id = _args.checkjstring(1);
        if(SnmpLib._sessions.containsKey(_id))
        {
            SnmpSession _session = SnmpLib._sessions.get(_id);
            SnmpLib._sessions.remove(_id);
            _session.close();

            return LuaBoolean.valueOf(true);
        }
        return LuaBoolean.valueOf(false);
    }

    public static Varargs _closeAll(Varargs _args)
    {
        for(SnmpSession _session : SnmpLib._sessions.values())
        {
            _session.close();
        }
        SnmpLib._sessions.clear();
        return LuaBoolean.valueOf(true);
    }

    public static Varargs _getall(Varargs _args)
    {
        LuaValue _sessionid = _args.arg(1);
        LuaValue _timeout = _args.arg(2);

        String _id = _sessionid.checkjstring();
        SnmpSession _session = SnmpLib._sessions.get(_id);

        SnmpPduRequest _pdu = new SnmpPduRequest(SnmpPduRequest.GET);
        for(int _i = 3; _i<=_args.narg(); _i++)
        {
            _pdu.addVarBind(new SnmpVarBind(_args.arg(_i).checkjstring()));
        }

        _SnmpHandler _h = _SnmpHandler.create();
        try
        {
            long _i = _timeout.checklong();
            long _j = (_i>>9)+10;

            _session.send(_pdu,_h);

            while(_h._finished==false && _j<_i)
            {
                Thread.sleep(_j);
                _j=_j<<1;
            }
        }
        catch (InterruptedException _e)
        {
            return LuaValue.NIL;
        }

        return _h._result;
    }

    public static Varargs _next(Varargs _args)
    {
        String _id = _args.checkjstring(1);
        SnmpSession _session = SnmpLib._sessions.get(_id);
        SnmpPduRequest _pdu = new SnmpPduRequest(SnmpPduRequest.GETNEXT);
        _pdu.addVarBind(new SnmpVarBind(_args.checkjstring(2)));

        _SnmpHandler _h = _SnmpHandler.create();

        try
        {
            long _i = _args.optlong(3,5192L);
            long _j = (_i>>9)+10;

            _session.send(_pdu,_h);

            while(_h._finished==false && _j<_i)
            {
                Thread.sleep(_j);
                _j=_j<<1;
            }
        }
        catch (InterruptedException _e)
        {
            return LuaValue.NIL;
        }

        LuaValue _ret = _h._result.get(1);
        if(_ret.istable())
        {
            if(_ret.checktable().get(4).isnil())
            {
                return LuaValue.NIL;
            }
            return _ret;
        }
        return LuaValue.NIL;
    }

    public static Varargs _walk(Varargs _args)
    {
        String _id = _args.checkjstring(1);
        String _prefix = _args.checkjstring(2);
        String _oid = _prefix;
        SnmpSession _session = SnmpLib._sessions.get(_id);
        long _i = _args.optlong(3,5192L);
        long _t = _args.optlong(4,-1);

        _SnmpHandler _h = _SnmpHandler.create();

        while(_oid!=null)
        {
            try
            {
                long _j = (_i>>9)+10;
                SnmpPduRequest _pdu = new SnmpPduRequest(SnmpPduRequest.GETNEXT);
                _pdu.addVarBind(new SnmpVarBind(_oid));
                _h._finished=false;
                _session.send(_pdu,_h);

                while(_h._finished==false && _j<_i)
                {
                    Thread.sleep(_j);
                    _j=_j<<1;
                }

                _oid = _h._result.last().checktable().get(1).tojstring();
                if(!_oid.startsWith(_prefix))
                {
                    _h._result.removeLast();
                    _oid=null;
                }
                else
                if(_t>0L)
                {
                    Thread.sleep(_t);
                }
            }
            catch (InterruptedException _e)
            {
                return LuaValue.NIL;
            }
        }

        return _h._result;
    }

    public static Varargs _get(Varargs _args)
    {
        String _id = _args.checkjstring(1);

        SnmpSession _session = SnmpLib._sessions.get(_id);
        SnmpPduRequest _pdu = new SnmpPduRequest(SnmpPduRequest.GET);
        _pdu.addVarBind(new SnmpVarBind(_args.checkjstring(2)));

        _SnmpHandler _h = _SnmpHandler.create();

        try
        {
            long _i = _args.optlong(3, 5192L);
            long _j = (_i>>9)+10;

            _session.send(_pdu,_h);

            while(_h._finished==false && _j<_i)
            {
                Thread.sleep(_j);
                _j=_j<<1;
            }
        }
        catch (InterruptedException _e)
        {
            return LuaValue.NIL;
        }

        LuaValue _ret = _h._result.get(1);
        if(_ret.istable())
        {
            if(_ret.checktable().get(4).isnil())
            {
                return LuaValue.NIL;
            }
            return _ret;
        }
        return LuaValue.NIL;
    }

    @SneakyThrows
    public static Varargs _createSnmpSession(Varargs _args)
    {
        String _community = _args.optjstring(3,"public");
        int _port = _args.optint(2,161);
        String _hostAddress = _args.optjstring(1,"127.0.0.1");

        String _id = GuidUtil.toGUID(_hostAddress, _port);

        if(!SnmpLib._sessions.containsKey(_id))
        {
            SnmpPeer _peer = new SnmpPeer(InetAddress.getByName(_hostAddress), _port);
            SnmpParameters _params = new SnmpParameters(SnmpSMI.SNMPV2);
            _params.setReadCommunity(_community);
            _peer.setParameters(_params);
            SnmpSession _session = new SnmpSession(_peer);
            SnmpLib._sessions.put(_id, _session);
        }
        return LuaString.valueOf(_id);
    }


    static class _SnmpHandler implements SnmpHandler
    {
        static _SnmpHandler create() { return new _SnmpHandler(); }

        public boolean _finished = false;
        public LuaTable _result = new LuaTable();
        @Override
        public void snmpReceivedPdu(SnmpSession snmpSession, int i, SnmpPduPacket snmpPduPacket)
        {
            for(SnmpVarBind _vb : snmpPduPacket.toVarBindArray())
            {
                LuaTable _tab = new LuaTable();
                _tab.set(1, LuaString.valueOf(_vb.getName().toString()));
                _tab.set(2, LuaInteger.valueOf(_vb.getValue().typeId()));
                _tab.set(3, LuaString.valueOf(_vb.getValue().toString()));
                _tab.set(4, snmpToLuaValue(_vb.getValue()));
                _result.insert(0, _tab);
            }
            _finished = true;
        }

        @Override
        public void snmpInternalError(SnmpSession snmpSession, int i, SnmpSyntax snmpSyntax) {
            _result.set(1, LuaString.valueOf(snmpSyntax.toString()));
            _result.set(2, LuaInteger.valueOf(-1));
            _result.set(3, LuaString.valueOf(""));
            _result.set(4, snmpToLuaValue(snmpSyntax));
            _finished = true;
        }

        @Override
        public void snmpTimeoutError(SnmpSession snmpSession, SnmpSyntax snmpSyntax) {
            _result.set(1, LuaString.valueOf(snmpSyntax.toString()));
            _result.set(2, LuaInteger.valueOf(-1));
            _result.set(3, LuaString.valueOf(""));
            _result.set(4, snmpToLuaValue(snmpSyntax));
            _finished = true;
        }
    }

    public static final LuaValue snmpToLuaValue(SnmpSyntax _value)
    {
        switch(_value.typeId())
        {
            case -128: return LuaValue.NIL; // SnmpNoSuchObject();
            case -127: return LuaValue.NIL; // SnmpNoSuchInstance();
            case -126: return LuaValue.NIL; // SnmpEndOfMibView();
            case 2: return LuaInteger.valueOf(((SnmpInt32)_value).getValue()); // SnmpInt32();
            case 4: return LuaString.valueOf(((SnmpOctetString)_value).getString()); // SnmpOctetString();
            case 5: return LuaNil.NIL; // SnmpNull();
            case 6: return LuaString.valueOf(((SnmpObjectId)_value).toString()); //  SnmpObjectId();
            case 64: return LuaString.valueOf(((SnmpIPAddress)_value).toString()); //   SnmpIPAddress();
            case 65: return LuaInteger.valueOf(((SnmpCounter32)_value).getValue()); // SnmpCounter32();
            case 66: return LuaInteger.valueOf(((SnmpGauge32)_value).getValue()); // SnmpGauge32();
            case 67: return LuaInteger.valueOf(((SnmpTimeTicks)_value).getValue()); // SnmpTimeTicks();
            case 68: return LuaString.valueOf(((SnmpOpaque)_value).getString()); // SnmpOpaque();
            case 70: return LuaInteger.valueOf(((SnmpCounter64)_value).getValue().longValue()); // SnmpCounter64();
            default:
                return LuaString.valueOf(_value.toString());
        }
    }

}

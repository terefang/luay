package luay.lib.ext.more;
/*
%!begin MARKDOWN

## lfs/filesys

posix file system functions mostly inspired by libUseful and zdf lfs.

### Functions

*   `lfs.basename(Path) -> map, list` \
    gets a filename (basename) from a path
*   `lfs.dirname(Path)` \
    gets a directory part of a path, clipping off the last part that should be the filename
*   `lfs.filename(Path)` \
    gets a file name from a path, this is name without extension, so distinct from basename
*   `lfs.exists(Path)` \
    return true if a filesystem object (file, directory, etc) exists at path 'Path', false otherwise
*   `lfs.extn(Path)` \
    gets a file extension from a path
*   `lfs.mkdir(Path)` \
    make a directory. DirMask is the 'mode' of the created directory, and is optional
*   `lfs.mkdirPath(Path)` \
    make a directory, CREATING ALL PARENT DIRECTORIES AS NEEDED.
*   `lfs.size(Path)` \
    get size of a file
*   `lfs.mtime(Path)` \
    get modification time of a file
*   `lfs.chown(Path, Owner)` \
    change owner of a file. 'Owner' is the name, not the uid
*   `lfs.chgrp(Path, Group)` \
    change group of a file. 'Group' is the group name, not the gid
*   `lfs.chmod(Path, Mode)` \
    change mode/permissions of a file.
    Perms can be a numeric value like '0666' or rwx string like 'rw-rw-rw'
*   `lfs.rename(from, to)
*   `lfs.rmdir(path)` \
    remove directory. Directory must be empty
*   `lfs.copy(src, dest)` \
    make a copy of a file
*   `lfs.copydir(src, dest)` \
    make a recursive copy of a directory
*   `lfs.find(File,path) -> path`
*   `lfs.symlink(path, symlink)` \
    create a symbolic link at 'symlink' pointing to file/directory at 'path'
*   `lfs.link(path, linkname)` \
    create a hard link at 'linkname' pointing to file/directory at 'path'
*   `lfs.is_file(filename) -> boolean`
*   `lfs.is_dir(filename) -> boolean`
*   `lfs.list_dirs(dirname [, ext, ...]) -> list`
*   `lfs.list_dir(dirname [, ext, ...]) -> list`

### TODO ZDF FS

abspath
appendfile
copyfile
currentdir
cutpath
delimiter
dirfiles
dirhas
dirif
dirmatch
dirtree
extname
filelist
findup
isabsolute
isdir
isdodd
isfile
isleafdir
isposixname
isreadable
iswindowsname
joinpath
longextname
mkdirtree
mkpath
normpath
readargsfile
readfile
readlines
readmxtfile
relativepath
rmdirtree
rootprefix
separator
shortfilename
splitpath
subdirs
syspath
unixpath
winpath
writefile
writelines

\pagebreak

%!end MARKDOWN

 */
import luay.lib.ext.AbstractLibrary;
import luay.lib.LuayLibraryFactory;
import luay.vm.*;
import luay.vm.lib.OneArgFunction;
import luay.vm.lib.TwoArgFunction;
import luay.vm.lib.VarArgFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.*;

public class FilesysLib extends AbstractLibrary implements LuayLibraryFactory
{
    @Override
    public List<Class> getLibraryFunctionClasses() {
        return Arrays.asList(_basename.class,
                _dirname.class,
                _filename.class,
                _exists.class,
                _extn.class,
                _rmdir.class,
                _mkdir.class,
                _mkdirp.class,
                _size.class,
                _chown.class,
                _chgrp.class,
                _chmod.class,
                _rename.class,
                _copy.class,
                _copydir.class,
                _find.class,
                _link.class,
                _symlink.class,
                _is_dir.class,
                _is_file.class,
                _list_dir.class,
                _list_dirs.class
        );
    }

    @Override
    public String getLibraryName() {
        return "lfs";
    }

    /*  This module provides various functions related to filesystem access
     */

    public FilesysLib()
    {
    }

    /*  lfs.basename(Path)   gets a filename (basename) from a path*/
    static final class _basename extends OneArgFunction
    {
        @Override
        public LuaValue call(LuaValue arg)
        {
            return LuaString.valueOf(new File(arg.checkjstring()).getName());
        }
    }

    /*  lfs.dirname(Path)   gets a directory part of a path, clipping off the last part that should be the filename */
    static final class _dirname extends OneArgFunction
    {
        @Override
        public LuaValue call(LuaValue arg)
        {
            return LuaString.valueOf(new File(arg.checkjstring()).getParent());
        }
    }

    /*  lfs.filename(Path)   gets a file name from a path, this is name without extension, so distinct from basename*/
    static final class _filename extends OneArgFunction
    {
        @Override
        public LuaValue call(LuaValue arg)
        {
            String _str = new File(arg.checkjstring()).getName();
            int _ofs = 0;
            if((_ofs = _str.lastIndexOf('.'))>0)
            {
                _str = _str.substring(0, _ofs);
            }
            return LuaString.valueOf(_str);
        }
    }

    /*  lfs.exists(Path)   return true if a filesystem object (file, directory, etc) exists at path 'Path', false otherwise */
    static final class _exists extends OneArgFunction
    {
        @Override
        public LuaValue call(LuaValue arg)
        {
            return LuaBoolean.valueOf(new File(arg.checkjstring()).exists());
        }
    }

    /*  lfs.extn(Path)   gets a file extension from a path*/
    static final class _extn extends OneArgFunction
    {
        @Override
        public LuaValue call(LuaValue arg)
        {
            String _str = new File(arg.checkjstring()).getName();
            int _ofs = 0;
            if((_ofs = _str.lastIndexOf('.'))>0)
            {
                LuaString.valueOf(_str.substring(_ofs));
            }
            return LuaString.valueOf("");
        }
    }

    /*  lfs.mkdir(Path)   make a directory. DirMask is the 'mode' of the created directory, and is optional */
    static final class _mkdir extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs _args)
        {
            File _file = new File(_args.checkjstring(1));
            return LuaBoolean.valueOf(_file.mkdir());
        }
    }

    /*  lfs.mkdirPath(Path)   make a directory, CREATING ALL PARENT DIRECTORIES AS NEEDED.
        DirMask is the 'mode' of the created directory, and is optional */
    static final class _mkdirp extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs _args)
        {
            File _file = new File(_args.checkjstring(1));
            return LuaBoolean.valueOf(_file.mkdirs());
        }
    }

    /* lfs.size(Path)   get size of a file */
    static final class _size extends OneArgFunction
    {
        @Override
        public LuaValue call(LuaValue arg)
        {
            return LuaInteger.valueOf(new File(arg.checkjstring()).length());
        }
    }

    /* lfs.mtime(Path)   get modification time of a file */
    static final class _mtime extends OneArgFunction
    {
        @Override
        public LuaValue call(LuaValue arg)
        {
            return LuaInteger.valueOf(new File(arg.checkjstring()).lastModified());
        }
    }

    /*   lfs.chown(Path, Owner)   change owner of a file. 'Owner' is the name, not the uid */
    static final class _chown extends TwoArgFunction
    {
        @Override
        public LuaValue call(LuaValue _arg1, LuaValue _arg2)
        {
            try
            {
                Files.getFileAttributeView(new File(_arg1.checkjstring()).toPath(), PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
                        .setOwner(new UserPrincipal() {
                            @Override
                            public String getName() {
                                return _arg2.checkjstring();
                            }
                        });
                return LuaBoolean.valueOf(true);
            }
            catch (Exception _xe) { /* ignore */}

            return LuaBoolean.valueOf(false);
        }
    }

    /*   lfs.chgrp(Path, Group)   change group of a file. 'Group' is the group name, not the gid */
    static final class _chgrp extends TwoArgFunction
    {
        @Override
        public LuaValue call(LuaValue _arg1, LuaValue _arg2)
        {
            try
            {
                Files.getFileAttributeView(new File(_arg1.checkjstring()).toPath(), PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
                        .setGroup(new GroupPrincipal() {
                            @Override
                            public String getName() {
                                return _arg2.checkjstring();
                            }
                        });
                return LuaBoolean.valueOf(true);
            }
            catch (Exception _xe) { /* ignore */}

            return LuaBoolean.valueOf(false);
        }
    }

    /*   lfs.chmod(Path, Mode)   change mode/permissions of a file.
    Perms can be a numeric value like '0666' or rwx string like 'rw-rw-rw' */
    static final class _chmod extends TwoArgFunction
    {
        @Override
        public LuaValue call(LuaValue _arg1, LuaValue _arg2)
        {
            String _p = _arg2.checkjstring();

            Set<PosixFilePermission> _perm = new HashSet<>();
            if(_p.startsWith("0"))
            {
                int _usrp = Integer.parseInt(_p.substring(1,2));
                int _grpp = Integer.parseInt(_p.substring(2,3));
                int _othp = Integer.parseInt(_p.substring(3,4));

                if((_usrp & 4) == 4) _perm.add(PosixFilePermission.OWNER_READ);
                if((_usrp & 2) == 2) _perm.add(PosixFilePermission.OWNER_WRITE);
                if((_usrp & 1) == 1) _perm.add(PosixFilePermission.OWNER_EXECUTE);

                if((_grpp & 4) == 4) _perm.add(PosixFilePermission.GROUP_READ);
                if((_grpp & 2) == 2) _perm.add(PosixFilePermission.GROUP_WRITE);
                if((_grpp & 1) == 1) _perm.add(PosixFilePermission.GROUP_EXECUTE);

                if((_othp & 4) == 4) _perm.add(PosixFilePermission.OTHERS_READ);
                if((_othp & 2) == 2) _perm.add(PosixFilePermission.OTHERS_WRITE);
                if((_othp & 1) == 1) _perm.add(PosixFilePermission.OTHERS_EXECUTE);
            }
            else
            {
                if("r".equalsIgnoreCase(_p.substring(0,1))) _perm.add(PosixFilePermission.OWNER_READ);
                if("w".equalsIgnoreCase(_p.substring(1,2))) _perm.add(PosixFilePermission.OWNER_WRITE);
                if("x".equalsIgnoreCase(_p.substring(2,3))) _perm.add(PosixFilePermission.OWNER_EXECUTE);

                if("r".equalsIgnoreCase(_p.substring(3,4))) _perm.add(PosixFilePermission.GROUP_READ);
                if("w".equalsIgnoreCase(_p.substring(4,5))) _perm.add(PosixFilePermission.GROUP_WRITE);
                if("x".equalsIgnoreCase(_p.substring(5,6))) _perm.add(PosixFilePermission.GROUP_EXECUTE);

                if("r".equalsIgnoreCase(_p.substring(6,7))) _perm.add(PosixFilePermission.OTHERS_READ);
                if("w".equalsIgnoreCase(_p.substring(7,8))) _perm.add(PosixFilePermission.OTHERS_WRITE);
                if("x".equalsIgnoreCase(_p.substring(8,9))) _perm.add(PosixFilePermission.OTHERS_EXECUTE);
            }

            try
            {
                Files.getFileAttributeView(new File(_arg1.checkjstring()).toPath(), PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
                        .setPermissions(_perm);
                return LuaBoolean.valueOf(true);
            }
            catch (Exception _xe) { /* ignore */}

            return LuaBoolean.valueOf(false);
        }
    }

    /* lfs.rename(from, to); */
    static final class _rename extends TwoArgFunction
    {
        @Override
        public LuaValue call(LuaValue _arg1, LuaValue _arg2)
        {
            String _ffrom = _arg1.checkjstring();
            String _fto = _arg2.checkjstring();

            return LuaBoolean.valueOf(new File(_ffrom).renameTo(new File(_fto)));
        }
    }

    /* lfs.rmdir(path)    remove directory. Directory must be empty */
    static final class _rmdir extends OneArgFunction
    {
        @Override
        public LuaValue call(LuaValue _arg)
        {
            String _dir = _arg.checkjstring();

            return LuaBoolean.valueOf(new File(_dir).delete());
        }
    }

    /*  lfs.copy(src, dest)     make a copy of a file */
    static final class _copy extends TwoArgFunction
    {
        @Override
        public LuaValue call(LuaValue _arg1, LuaValue _arg2)
        {
            String _ffrom = _arg1.checkjstring();
            String _fto = _arg2.checkjstring();

            try
            {
                return LuaBoolean.valueOf(Files.copy(new File(_ffrom).toPath(),new File(_fto).toPath()).toFile().exists());
            }
            catch (Exception _xe) { /* ignore */}
            return LuaBoolean.valueOf(false);
        }
    }

    /*  lfs.copydir(src, dest)     make a recursive copy of a directory */
    static final class _copydir extends TwoArgFunction
    {
        @Override
        public LuaValue call(LuaValue _arg1, LuaValue _arg2)
        {
            String _ffrom = _arg1.checkjstring();
            String _fto = _arg2.checkjstring();

            try
            {
                Files.walk(Paths.get(_ffrom))
                        .forEach(source -> {
                            Path destination = Paths.get(_fto, source.toString()
                                    .substring(_ffrom.length()));
                            try {
                                Files.copy(source, destination);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                return LuaBoolean.valueOf(true);
            }
            catch (Exception _xe) { /* ignore */}
            return LuaBoolean.valueOf(false);
        }
    }

    /*  Path=lfs.find(File,path */
    static final class _find extends TwoArgFunction
    {
        @Override
        public LuaValue call(LuaValue _arg1, LuaValue _arg2)
        {
            String _file = _arg1.checkjstring();
            String _path = _arg2.checkjstring();

            try
            {
                return LuaString.valueOf(Files.find(Paths.get(_path),Integer.MAX_VALUE,(_found, _attr)->{
                    return _found.toFile().getName().equalsIgnoreCase(_file);
                }).findFirst().get().toFile().getAbsolutePath());
            }
            catch (Exception _xe) { /* ignore */}
            return LuaBoolean.valueOf(false);
        }
    }

    /*  lfs.symlink(path, symlink)   create a symbolic link at 'symlink' pointing to file/directory at 'path' */
    static final class _symlink extends TwoArgFunction
    {
        @Override
        public LuaValue call(LuaValue _arg1, LuaValue _arg2)
        {
            String _ffrom = _arg1.checkjstring();
            String _fto = _arg2.checkjstring();

            try
            {
                return LuaBoolean.valueOf(Files.createSymbolicLink(Paths.get(_ffrom),Paths.get(_fto)).toFile().exists());
            }
            catch (Exception _xe) { /* ignore */}
            return LuaBoolean.valueOf(false);
        }
    }

    /*  lfs.link(path, linkname)     create a hard link at 'linkname' pointing to file/directory at 'path' */
    static final class _link extends TwoArgFunction
    {
        @Override
        public LuaValue call(LuaValue _arg1, LuaValue _arg2)
        {
            String _ffrom = _arg1.checkjstring();
            String _fto = _arg2.checkjstring();

            try
            {
                return LuaBoolean.valueOf(Files.createLink(Paths.get(_ffrom),Paths.get(_fto)).toFile().exists());
            }
            catch (Exception _xe) { /* ignore */}
            return LuaBoolean.valueOf(false);
        }
    }



    /*  lfs.pathaddslash(Path)   append a '/' to a path if it doesn't already have one */

    /*  lfs.pathdelslash(Path)   remove a '/' from end of a path */

    /*  lfs.newExtn(Path, NewExtn)   change the ms-dos style extension of a file. Adds one to files that have no extension */



        /*  lfs.mount(device, mountpoint, type, args)  mount a filesystem. 'args' is optional.
        If the mount point doesn't exist, it will be created.
        This also (linux only) supports 'bind mounts' where 'dev' becomes a directory, and 'type' is 'bind'.
        Bind mounts are a means of mounting a directory onto another directory, so the 'mount point' directory contains the same
        items as the 'dev' directory.
        'args' is an optional, space-separated list of
        ro       - readonly mount
        rw       - read/write mount
        noatime  - don't record file access times
        nodiratime  - don't record file access times
        noexec   - don't allow files to be executed on the mounted filesystem
        nosuid   - don't honor suid binaries on the mounted filesystem
        nodev    - don't honor device files on the mounted filesystem
        remount  - remount if already mounted
        perms    - permissions for mount-point if it has to be created
        */

        /*  lfs.unmount(mountpoint, args)  Unmount a filesystem. 'args' is optional and can be a space seperated list of
        detach   - use 'MNT_DETACH' to do a lazy unmount, which means if filesystem is busy it will be marked for unmounting and unmount when ceases to be busy
        lazy     - use 'MNT_DETACH' to do a lazy unmount, which means if filesystem is busy it will be marked for unmounting and unmount when ceases to be busy
        recurse  - umount any mounts under the mount point as well as the mount point. Without this the mount will fail if other mounts are active under it
        rmdir    - remove mountpoint directory after unmounting
        */

        /*
        Object for implementing file system globbing. Works like:
        Glob=lfs.GLOB("*.txt")
        item=Glob:next()
        while item ~= nil
        do
        Info=Glob:info();
        print("name: " .. item)
        print("type: " .. Info.type)
        print("size: " .. Info.size)
        print("sha1: " .. Glob:hash("sha1"))
        item=Glob:next()
        end
        */

    // is_file(filename) -> boolean
    static final class _is_file extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue arg1)
        {
            return LuaBoolean.valueOf(new File(arg1.checkjstring()).isFile());
        }
    }

    // is_dir(filename) -> boolean
    static final class _is_dir extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue arg1)
        {
            return LuaBoolean.valueOf(new File(arg1.checkjstring()).isDirectory());
        }
    }

    // list_dirs(dirname [, ext, ...]) -> list
    static final class _list_dirs extends VarArgFunction
    {
        @Override
        public Varargs invoke(Varargs args)
        {
            LuaTable _z = new LuaTable();
            Deque<File> _queue = new LinkedList<>();
            String _base = new File(args.checkjstring(1)).getAbsolutePath();
            _queue.add(new File(_base));

            while(_queue.size()>0)
            {
                for(File _f : _queue.pollLast().listFiles())
                {
                    if(_f.isDirectory()) _queue.push(_f);

                    boolean _found = false;

                    if(args.narg()==1)
                    {
                        _found=true;
                    }
                    else
                    {
                        for(int _i=1; _i<args.narg(); _i++)
                        {
                            if(_f.getName().endsWith(args.checkjstring(_i+1)))
                            {
                                _found=true;
                                break;
                            }
                        }
                    }

                    if(_found)
                    {
                        _z.insert(_z.length()+1, LuaString.valueOf(_f.getAbsolutePath().substring(_base.length())));
                    }
                }
            }

            return _z;
        }
    }

    // list_dir(dirname [, ext, ...]) -> list
    static final class _list_dir extends VarArgFunction
    {
        @Override
        public Varargs invoke(Varargs args)
        {
            LuaTable _z = new LuaTable();

            for(File _f : new File(args.checkjstring(1)).listFiles())
            {
                boolean _found = false;

                if(args.narg()==1)
                {
                    _found=true;
                }
                else
                {
                    for(int _i=1; _i<args.narg(); _i++)
                    {
                        if(_f.getName().endsWith(args.checkjstring(_i+1)))
                        {
                            _found=true;
                            break;
                        }
                    }
                }

                if(_found)
                {
                    _z.insert(_z.length()+1, LuaString.valueOf(_f.getName()));
                }
            }

            return _z;
        }
    }
}



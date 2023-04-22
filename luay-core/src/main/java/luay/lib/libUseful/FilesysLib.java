package luay.lib.libUseful;

import luay.vm.LuaFunction;
import luay.vm.LuaValue;
import luay.vm.Varargs;
import luay.vm.luay.AbstractLibrary;
import luay.vm.luay.LuayLibraryFactory;

import java.util.List;

public class FilesysLib extends AbstractLibrary  implements LuayLibraryFactory
{
    @Override
    public String getLibraryName() {
        return "filesys";
    }

    @Override
    public List<LuaFunction> getLibraryFunctions() {
        return toList(
                _varArgFunctionWrapper.from("___", FilesysLib::___)
        );
    }

    public static LuaValue ___(Varargs args)
    {
        return LuaValue.NONE;
    }

    // filesys.rename(from, to);
    // filesys.pathaddslash(Path)   remove a '/' from end of a path
    /*  filesys.basename(Path)   gets a filename (basename) from a path*/
    /*  filesys.pathaddslash(Path)   append a '/' to a path if it doesn't already have one */
    /*  filesys.pathdelslash(Path)   remove a '/' from end of a path */
    /*  filesys.exists(Path)   return true if a filesystem object (file, directory, etc) exists at path 'Path', false otherwise */
    /*  filesys.extn(Path)   gets a file extension from a path*/
    /*  filesys.filename(Path)   gets a file name from a path, this is name without extension, so distinct from basename*/
    /*  filesys.dirname(Path)   gets a directory part of a path, clipping off the last part that should be the filename */
    /* filesys.mtime(Path)   get modification time of a file */
    /* filesys.size(Path)   get size of a file */
    /*  filesys.mkdir(Path,DirMask="0744")   make a directory. DirMask is the 'mode' of the created directory, and is optional */
    /*  filesys.mkdirPath(Path,DirMask="0744")   make a directory, CREATING ALL PARENT DIRECTORIES AS NEEDED.  DirMask is the 'mode' of the created directory, and is optional */
    /* filesys.rmdir(path)    remove directory. Directory must be empty */
    /*  Path=filesys.find(File, Path) */
    /*   filesys.chown(Path, Owner)   change owner of a file. 'Owner' is the name, not the uid */
    //  filesys.chgrp(Path, Group)   change group of a file. 'Group' is the group name, not the gid
    //  filesys.chmod(Path, Mode)   change mode/permissions of a file. Perms can be a numeric value like '0666' or rwx string like 'rw-rw-rw'
    //  filesys.copy(src, dest)     make a copy of a file
    //  filesys.copydir(src, dest)     make a recursive copy of a directory */
    //  filesys.newExtn(Path, NewExtn)
    //  filesys.touch(path)
    //  filesys.symlink(path, symlink)
    //  filesys.link(path, linkname)
    //  filesys.unlink(path)
    //  filesys.fs_size(Path)
    //  filesys.fs_used(Path)
    //  filesys.fs_free(Path)
    //  filesys.glob(Path, fnmatch) -> list

}

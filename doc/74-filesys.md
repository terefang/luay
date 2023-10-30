## filesys

posix file system functions mostly inspired by libUseful.

### Functions

*   `filesys.basename(Path) -> map, list` \
    gets a filename (basename) from a path
*   `filesys.dirname(Path)` \
    gets a directory part of a path, clipping off the last part that should be the filename
*   `filesys.filename(Path)` \
    gets a file name from a path, this is name without extension, so distinct from basename
*   `filesys.exists(Path)` \
    return true if a filesystem object (file, directory, etc) exists at path 'Path', false otherwise 
*   `filesys.extn(Path)` \
    gets a file extension from a path
*   `filesys.mkdir(Path)` \
    make a directory. DirMask is the 'mode' of the created directory, and is optional
*   `filesys.mkdirPath(Path)` \
    make a directory, CREATING ALL PARENT DIRECTORIES AS NEEDED.
*   `filesys.size(Path)` \
    get size of a file
*   `filesys.mtime(Path)` \
    get modification time of a file
*   `filesys.chown(Path, Owner)` \
    change owner of a file. 'Owner' is the name, not the uid
*   `filesys.chgrp(Path, Group)` \
    change group of a file. 'Group' is the group name, not the gid
*   `filesys.chmod(Path, Mode)` \
    change mode/permissions of a file.
    Perms can be a numeric value like '0666' or rwx string like 'rw-rw-rw'
*   `filesys.rename(from, to)
*   `filesys.rmdir(path)` \
    remove directory. Directory must be empty
*   `filesys.copy(src, dest)` \
    make a copy of a file
*   `filesys.copydir(src, dest)` \
    make a recursive copy of a directory
*   `filesys.find(File,path) -> path`
*   `filesys.symlink(path, symlink)` \
    create a symbolic link at 'symlink' pointing to file/directory at 'path'
*   `filesys.link(path, linkname)` \
    create a hard link at 'linkname' pointing to file/directory at 'path'
*   `filesys.is_file(filename) -> boolean` 
*   `filesys.is_dir(filename) -> boolean`
*   `filesys.list_dirs(dirname [, ext, ...]) -> list`
*   `filesys.list_dir(dirname [, ext, ...]) -> list`

\pagebreak
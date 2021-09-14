package com.liu.jvmByteCode.ResovleByteCode;

import com.liu.jvmByteCode.ResovleByteCode.type.U2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ResolveMain {
    public static void main(String[] args) throws Exception {
        ResolveMain resolveMain = new ResolveMain();
        //读取文件
        ByteBuffer byteBuffer = resolveMain.readFile("D:\\workspace\\ruyuan-dfs\\ruyuan-dfs-common\\target\\classes\\com\\ruyuan\\dfs\\model\\backup\\BackupNodeInfo.class");
        //依次解析
        ClassFileAnalysister analysister = new ClassFileAnalysister();
        analysister.parse(byteBuffer);
//        U2 magicInfo = new U2(byteBuffer.get(),byteBuffer.get());
//        System.out.println(magicInfo.toInt());
//        System.out.println(magicInfo.toHexString());
    }

    /**
     * 读取文件
     * @param fileName
     * @return
     */
    public ByteBuffer readFile(String fileName) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        FileChannel channel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        channel.read(byteBuffer);
        byteBuffer.flip();
        return byteBuffer;
    }
}

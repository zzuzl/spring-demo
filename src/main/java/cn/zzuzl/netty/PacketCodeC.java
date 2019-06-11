package cn.zzuzl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class PacketCodeC {
    public static final PacketCodeC INSTANCE = new PacketCodeC();
    private static final int MAGIC_NUMBER = 0x12345678;

    public ByteBuf encode(Packet packet) {
        // 创建ByteBuf对象
        ByteBuf buf = ByteBufAllocator.DEFAULT.ioBuffer();
        encode(buf, packet);

        return buf;
    }

    public void encode(ByteBuf buf, Packet packet) {
        // 序列化对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 实际编码过程
        buf.writeInt(MAGIC_NUMBER);
        buf.writeByte(packet.getVersion());
        buf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buf.writeByte(packet.getCommand());
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        if (serializeAlgorithm == SerializerAlgorithm.JSON) {
            return new JSONSerializer();
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        if (command == Command.LOGIN_REQUEST) {
            return LoginRequestPacket.class;
        } else if (command == Command.LOGIN_RESPONSE) {
            return LoginResponsePacket.class;
        } else if (command == Command.MESSAGE_REQUEST) {
            return MessageRequestPacket.class;
        } else if (command == Command.MESSAGE_RESPONSE) {
            return MessageResponsePacket.class;
        }
        return null;
    }
}

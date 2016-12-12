package org.whut.meterManagement.aes256;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-12-7
 * Time: 上午9:22
 * To change this template use File | Settings | File Templates.
 */
public class AES256 {
    //sbox数组
    private static byte[] sbox = {
            0x63, 0x7c, 0x77, 0x7b, (byte) 0xf2, 0x6b, 0x6f, (byte) 0xc5,
            0x30, 0x01, 0x67, 0x2b, (byte) 0xfe, (byte) 0xd7, (byte) 0xab, 0x76,
            (byte) 0xca, (byte) 0x82, (byte) 0xc9, 0x7d, (byte) 0xfa, 0x59, 0x47, (byte) 0xf0,
            (byte) 0xad, (byte) 0xd4, (byte) 0xa2, (byte) 0xaf, (byte) 0x9c, (byte) 0xa4, 0x72, (byte) 0xc0,
            (byte) 0xb7, (byte) 0xfd, (byte) 0x93, 0x26, 0x36, 0x3f, (byte) 0xf7, (byte) 0xcc,
            0x34, (byte) 0xa5, (byte) 0xe5, (byte) 0xf1, 0x71, (byte) 0xd8, 0x31, 0x15,
            0x04, (byte) 0xc7, 0x23, (byte) 0xc3, 0x18, (byte) 0x96, 0x05, (byte) 0x9a,
            0x07, 0x12, (byte) 0x80, (byte) 0xe2, (byte) 0xeb, 0x27, (byte) 0xb2, 0x75,
            0x09, (byte) 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, (byte) 0xa0,
            0x52, 0x3b, (byte) 0xd6, (byte) 0xb3, 0x29, (byte) 0xe3, 0x2f, (byte) 0x84,
            0x53, (byte) 0xd1, 0x00, (byte) 0xed, 0x20, (byte) 0xfc, (byte) 0xb1, 0x5b,
            0x6a, (byte) 0xcb, (byte) 0xbe, 0x39, 0x4a, 0x4c, 0x58, (byte) 0xcf,
            (byte) 0xd0, (byte) 0xef, (byte) 0xaa, (byte) 0xfb, 0x43, 0x4d, 0x33, (byte) 0x85,
            0x45, (byte) 0xf9, 0x02, 0x7f, 0x50, 0x3c, (byte) 0x9f, (byte) 0xa8,
            0x51, (byte) 0xa3, 0x40, (byte) 0x8f, (byte) 0x92, (byte) 0x9d, 0x38, (byte) 0xf5,
            (byte) 0xbc, (byte) 0xb6, (byte) 0xda, 0x21, 0x10, (byte) 0xff, (byte) 0xf3, (byte) 0xd2,
            (byte) 0xcd, 0x0c, 0x13, (byte) 0xec, 0x5f, (byte) 0x97, 0x44, 0x17,
            (byte) 0xc4, (byte) 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
            0x60, (byte) 0x81, 0x4f, (byte) 0xdc, 0x22, 0x2a, (byte) 0x90, (byte) 0x88,
            0x46, (byte) 0xee, (byte) 0xb8, 0x14, (byte) 0xde, 0x5e, 0x0b, (byte) 0xdb,
            (byte) 0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c,
            (byte) 0xc2, (byte) 0xd3, (byte) 0xac, 0x62, (byte) 0x91, (byte) 0x95, (byte) 0xe4, 0x79,
            (byte) 0xe7, (byte) 0xc8, 0x37, 0x6d, (byte) 0x8d, (byte) 0xd5, 0x4e, (byte) 0xa9,
            0x6c, 0x56, (byte) 0xf4, (byte) 0xea, 0x65, 0x7a, (byte) 0xae, 0x08,
            (byte) 0xba, 0x78, 0x25, 0x2e, 0x1c, (byte) 0xa6, (byte) 0xb4, (byte) 0xc6,
            (byte) 0xe8, (byte) 0xdd, 0x74, 0x1f, 0x4b, (byte) 0xbd, (byte) 0x8b, (byte) 0x8a,
            0x70, 0x3e, (byte) 0xb5, 0x66, 0x48, 0x03, (byte) 0xf6, 0x0e,
            0x61, 0x35, 0x57, (byte) 0xb9, (byte) 0x86, (byte) 0xc1, 0x1d, (byte) 0x9e,
            (byte) 0xe1, (byte) 0xf8, (byte) 0x98, 0x11, 0x69, (byte) 0xd9, (byte) 0x8e, (byte) 0x94,
            (byte) 0x9b, 0x1e, (byte) 0x87, (byte) 0xe9, (byte) 0xce, 0x55, 0x28, (byte) 0xdf,
            (byte) 0x8c, (byte) 0xa1, (byte) 0x89, 0x0d, (byte) 0xbf, (byte) 0xe6, 0x42, 0x68,
            0x41, (byte) 0x99, 0x2d, 0x0f, (byte) 0xb0, 0x54, (byte) 0xbb, 0x16
    };
    private static byte[] sboxinv = {
            0x52, 0x09, 0x6a, (byte) 0xd5, 0x30, 0x36, (byte) 0xa5, 0x38,
            (byte) 0xbf, 0x40, (byte) 0xa3, (byte) 0x9e, (byte) 0x81, (byte) 0xf3, (byte) 0xd7, (byte) 0xfb,
            0x7c, (byte) 0xe3, 0x39, (byte) 0x82, (byte) 0x9b, 0x2f, (byte) 0xff, (byte) 0x87,
            0x34, (byte) 0x8e, 0x43, 0x44, (byte) 0xc4, (byte) 0xde, (byte) 0xe9, (byte) 0xcb,
            0x54, 0x7b, (byte) 0x94, 0x32, (byte) 0xa6, (byte) 0xc2, 0x23, 0x3d,
            (byte) 0xee, 0x4c, (byte) 0x95, 0x0b, 0x42, (byte) 0xfa, (byte) 0xc3, 0x4e,
            0x08, 0x2e, (byte) 0xa1, 0x66, 0x28, (byte) 0xd9, 0x24, (byte) 0xb2,
            0x76, 0x5b, (byte) 0xa2, 0x49, 0x6d, (byte) 0x8b, (byte) 0xd1, 0x25,
            0x72, (byte) 0xf8, (byte) 0xf6, 0x64, (byte) 0x86, 0x68, (byte) 0x98, 0x16,
            (byte) 0xd4, (byte) 0xa4, 0x5c, (byte) 0xcc, 0x5d, 0x65, (byte) 0xb6, (byte) 0x92,
            0x6c, 0x70, 0x48, 0x50, (byte) 0xfd, (byte) 0xed, (byte) 0xb9, (byte) 0xda,
            0x5e, 0x15, 0x46, 0x57, (byte) 0xa7, (byte) 0x8d, (byte) 0x9d, (byte) 0x84,
            (byte) 0x90, (byte) 0xd8, (byte) 0xab, 0x00, (byte) 0x8c, (byte) 0xbc, (byte) 0xd3, 0x0a,
            (byte) 0xf7, (byte) 0xe4, 0x58, 0x05, (byte) 0xb8, (byte) 0xb3, 0x45, 0x06,
            (byte) 0xd0, 0x2c, 0x1e, (byte) 0x8f, (byte) 0xca, 0x3f, 0x0f, 0x02,
            (byte) 0xc1, (byte) 0xaf, (byte) 0xbd, 0x03, 0x01, 0x13, (byte) 0x8a, 0x6b,
            0x3a, (byte) 0x91, 0x11, 0x41, 0x4f, 0x67, (byte) 0xdc, (byte) 0xea,
            (byte) 0x97, (byte) 0xf2, (byte) 0xcf, (byte) 0xce, (byte) 0xf0, (byte) 0xb4, (byte) 0xe6, 0x73,
            (byte) 0x96, (byte) 0xac, 0x74, 0x22, (byte) 0xe7, (byte) 0xad, 0x35, (byte) 0x85,
            (byte) 0xe2, (byte) 0xf9, 0x37, (byte) 0xe8, 0x1c, 0x75, (byte) 0xdf, 0x6e,
            0x47, (byte) 0xf1, 0x1a, 0x71, 0x1d, 0x29, (byte) 0xc5, (byte) 0x89,
            0x6f, (byte) 0xb7, 0x62, 0x0e, (byte) 0xaa, 0x18, (byte) 0xbe, 0x1b,
            (byte) 0xfc, 0x56, 0x3e, 0x4b, (byte) 0xc6, (byte) 0xd2, 0x79, 0x20,
            (byte) 0x9a, (byte) 0xdb, (byte) 0xc0, (byte) 0xfe, 0x78, (byte) 0xcd, 0x5a, (byte) 0xf4,
            0x1f, (byte) 0xdd, (byte) 0xa8, 0x33, (byte) 0x88, 0x07, (byte) 0xc7, 0x31,
            (byte) 0xb1, 0x12, 0x10, 0x59, 0x27, (byte) 0x80, (byte) 0xec, 0x5f,
            0x60, 0x51, 0x7f, (byte) 0xa9, 0x19, (byte) 0xb5, 0x4a, 0x0d,
            0x2d, (byte) 0xe5, 0x7a, (byte) 0x9f, (byte) 0x93, (byte) 0xc9, (byte) 0x9c, (byte) 0xef,
            (byte) 0xa0, (byte) 0xe0, 0x3b, 0x4d, (byte) 0xae, 0x2a, (byte) 0xf5, (byte) 0xb0,
            (byte) 0xc8, (byte) 0xeb, (byte) 0xbb, 0x3c, (byte) 0x83, 0x53, (byte) 0x99, 0x61,
            0x17, 0x2b, 0x04, 0x7e, (byte) 0xba, 0x77, (byte) 0xd6, 0x26,
            (byte) 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
    };

    private byte F(byte x) {
        //System.out.println(x);
        int temp_x = Byte.toUnsignedInt(x);
        return (byte) (((temp_x) << 1) ^ ((((temp_x) >> 7) & 1) * 0x1b));
    }

    private byte FD(byte x) {
        //System.out.println(x);
        int temp_x = Byte.toUnsignedInt(x);
        return (byte) (((temp_x) >> 1) ^ (((temp_x) & 1) != 0 ? 0x8d : 0));
    }

    private byte gf_alog(byte x) // calculate anti-logarithm gen 3
    {
        //System.out.println(x);
        int atb = 1, z;
        int temp_x = Byte.toUnsignedInt(x);
        while (temp_x-- > 0) {
            z = atb;
            atb <<= 1;
            if ((z & 0x80) != 0)
                atb ^= 0x1b;
            atb ^= z;
        }
        return (byte) atb;
    }

    private byte gf_log(byte x) // calculate logarithm gen 3
    {
       // System.out.println(x);
        //int temp_x = Byte.toUnsignedInt(x);
        byte atb = 1, i = 0, z=0;
        //int temp_atb=atb;
        do {
            if (atb == x) break;
            z = (byte) atb;
            atb <<= 1;
            if ((z & 0x80) != 0) atb ^= 0x1b;
            atb ^= z;
        } while (++i > 0);

        return (byte) i;
    } /* gf_log */

    private byte gf_mulinv(byte x) // calculate multiplicative inverse
    {
        //int temp_x = Byte.toUnsignedInt(x);
        int y = Byte.toUnsignedInt(gf_log(x));
        return (x != 0) ? gf_alog((byte) (255 - y)) : (byte) 0;
    } /* gf_mulinv */
    private byte rj_sbox(byte x) {
        int y = Byte.toUnsignedInt(x);
        String s = Integer.toHexString(y);
        if(s.length()==1)
            s = "0"+s;
        int i,j;
       // System.out.println(s);
        i = Integer.parseInt(s.substring(0,1),16);
        j = Integer.parseInt(s.substring(1,2),16);
        return sbox[16*i+j];
        //System.out.println(x);
        //int temp_x = Byte.toUnsignedInt(x);
        /*byte y, sb;
        sb = y = gf_mulinv(x);
        int temp_sb = Byte.toUnsignedInt(sb);
        int temp_y = Byte.toUnsignedInt(y);
        temp_y = (byte) ((temp_y << 1) | (temp_y >>> 7));
        temp_sb ^= temp_y;
        temp_y = (byte) ((temp_y << 1) | (temp_y >>> 7));
        temp_sb ^= temp_y;
        temp_y = (byte) ((temp_y << 1) | (temp_y >>> 7));
        temp_sb ^= temp_y;
        temp_y = (byte) ((temp_y << 1) | (temp_y >>> 7));
        temp_sb ^= temp_y;
        return (byte) (temp_sb ^ 0x63);*/
    }

    private byte rj_sbox_inv(byte x) {
        int y = Byte.toUnsignedInt(x);
        String s = Integer.toHexString(y);
        if(s.length()==1)
            s = "0"+s;
        int i,j;
        // System.out.println(s);
        i = Integer.parseInt(s.substring(0,1),16);
        j = Integer.parseInt(s.substring(1,2),16);
        return sboxinv[16*i+j];
        /*byte y=0, sb=0;
        int temp_sb = Byte.toUnsignedInt(sb);
        int temp_y = Byte.toUnsignedInt(y);
        temp_y = (byte) (temp_y ^ 0x63);
        temp_sb = temp_y = (byte) ((temp_y << 1) | (temp_y >>> 7));
        temp_y = (byte) ((temp_y << 2) | (temp_y >>> 6));
        temp_sb ^= temp_y;
        temp_y = (byte) ((temp_y << 3) | (temp_y >>> 5));
        temp_sb ^= temp_y;
        return gf_mulinv((byte)temp_sb);*/
    } /* rj_sbox_inv */

    private byte rj_xtime(byte x) {
        return ((x & 0x80) != 0) ? (byte) ((x << 1) ^ 0x1b) : (byte) (x << 1);
    } /* rj_xtime */

    private void aes_subBytes(byte[] buf) {
        byte i = 16;
        while (i-- > 0) buf[i] = rj_sbox(buf[i]);
    } /* aes_subBytes */

    private void aes_subBytes_inv(byte[] buf) {
        byte i = 16;

        while (i-- > 0) buf[i] = rj_sbox_inv(buf[i]);
    } /* aes_subBytes_inv */

    private void aes_addRoundKey(byte[] buf, byte[] key) {
        byte i = 16;

        while (i-- > 0) buf[i] ^= key[i];
    } /* aes_addRoundKey */

    private void aes_addRoundKey(byte[] buf, byte[] key, int offset) {
        byte i = 16;

        while (i-- > 0) buf[i] ^= key[i + offset];
    } /* aes_addRoundKey */

    private void aes_addRoundKey_cpy(byte[] buf, byte[] key, byte[] cpk) {
        byte i = 16;

        while (i-- > 0) {
            buf[i] ^= (cpk[i] = key[i]);
            cpk[16 + i] = key[16 + i];
        }
    } /* aes_addRoundKey_cpy */

    private void aes_shiftRows(byte[] buf) {
        byte i, j; /* to make it potentially parallelable :) */

        i = buf[1];
        buf[1] = buf[5];
        buf[5] = buf[9];
        buf[9] = buf[13];
        buf[13] = i;
        i = buf[10];
        buf[10] = buf[2];
        buf[2] = i;
        j = buf[3];
        buf[3] = buf[15];
        buf[15] = buf[11];
        buf[11] = buf[7];
        buf[7] = j;
        j = buf[14];
        buf[14] = buf[6];
        buf[6] = j;

    } /* aes_shiftRows */

    void aes_shiftRows_inv(byte[] buf) {
        byte i, j; /* same as above :) */

        i = buf[1];
        buf[1] = buf[13];
        buf[13] = buf[9];
        buf[9] = buf[5];
        buf[5] = i;
        i = buf[2];
        buf[2] = buf[10];
        buf[10] = i;
        j = buf[3];
        buf[3] = buf[7];
        buf[7] = buf[11];
        buf[11] = buf[15];
        buf[15] = j;
        j = buf[6];
        buf[6] = buf[14];
        buf[14] = j;

    } /* aes_shiftRows_inv */

    private void aes_mixColumns(byte[] buf) {
        byte i, a, b, c, d, e;

        for (i = 0; i < 16; i += 4) {
            a = buf[i];
            b = buf[i + 1];
            c = buf[i + 2];
            d = buf[i + 3];
            e = (byte) (a ^ b ^ c ^ d);
            buf[i] ^= (byte) (e ^ rj_xtime((byte) (a ^ b)));
            buf[i + 1] ^= (byte) (e ^ rj_xtime((byte) (b ^ c)));
            buf[i + 2] ^= (byte) (e ^ rj_xtime((byte) (c ^ d)));
            buf[i + 3] ^= (byte) (e ^ rj_xtime((byte) (d ^ a)));
        }
    } /* aes_mixColumns */

    private void aes_mixColumns_inv(byte[] buf) {
        byte i, a, b, c, d, e, x, y, z;

        for (i = 0; i < 16; i += 4) {
            a = buf[i];
            b = buf[i + 1];
            c = buf[i + 2];
            d = buf[i + 3];
            e = (byte) (a ^ b ^ c ^ d);
            z = rj_xtime(e);
            x = (byte) (e ^ rj_xtime(rj_xtime((byte) (z ^ a ^ c))));
            y = (byte) (e ^ rj_xtime(rj_xtime((byte) (z ^ b ^ d))));
            buf[i] ^= (byte) (x ^ rj_xtime((byte) (a ^ b)));
            buf[i + 1] ^= (byte) (y ^ rj_xtime((byte) (b ^ c)));
            buf[i + 2] ^= (byte) (x ^ rj_xtime((byte) (c ^ d)));
            buf[i + 3] ^= (byte) (y ^ rj_xtime((byte) (d ^ a)));
        }
    } /* aes_mixColumns_inv */

    private byte aes_expandEncKey(byte[] k, byte rc) {
        byte i;

        k[0] ^= (byte) (rj_sbox(k[29]) ^ (rc));
        k[1] ^= rj_sbox(k[30]);
        k[2] ^= rj_sbox(k[31]);
        k[3] ^= rj_sbox(k[28]);
        rc = F(rc);

        for (i = 4; i < 16; i += 4) {
            k[i] ^= k[i - 4];
            k[i + 1] ^= k[i - 3];
            k[i + 2] ^= k[i - 2];
            k[i + 3] ^= k[i - 1];
        }
        k[16] ^= rj_sbox(k[12]);
        k[17] ^= rj_sbox(k[13]);
        k[18] ^= rj_sbox(k[14]);
        k[19] ^= rj_sbox(k[15]);

        for (i = 20; i < 32; i += 4) {
            k[i] ^= k[i - 4];
            k[i + 1] ^= k[i - 3];
            k[i + 2] ^= k[i - 2];
            k[i + 3] ^= k[i - 1];
        }
        return rc;
    } /* aes_expandEncKey */

    private byte aes_expandDecKey(byte[] k, byte rc) {
        byte i;

        for (i = 28; i > 16; i -= 4) {
            k[i] ^= k[i - 4];
            k[i + 1] ^= k[i - 3];
            k[i + 2] ^= k[i - 2];
            k[i + 3] ^= k[i - 1];
        }

        k[16] ^= rj_sbox(k[12]);
        k[17] ^= rj_sbox(k[13]);
        k[18] ^= rj_sbox(k[14]);
        k[19] ^= rj_sbox(k[15]);

        for (i = 12; i > 0; i -= 4) {
            k[i] ^= k[i - 4];
            k[i + 1] ^= k[i - 3];
            k[i + 2] ^= k[i - 2];
            k[i + 3] ^= k[i - 1];
        }

        rc = FD(rc);
        k[0] ^= (byte) (rj_sbox(k[29]) ^ (rc));
        k[1] ^= rj_sbox(k[30]);
        k[2] ^= rj_sbox(k[31]);
        k[3] ^= rj_sbox(k[28]);
        return rc;
    } /* aes_expandDecKey */

    public void aes256_init(AES256Context ctx, byte[] k) {
        byte rcon = 1;
        byte i;
        ctx.key = new byte[32];
        ctx.enkey = new byte[32];
        ctx.deckey = new byte[32];

        for (i = 0; i < 32; i++)
            ctx.enkey[i] = ctx.deckey[i] = k[i];
        for (i = 8; --i > 0; )
            rcon = aes_expandEncKey(ctx.deckey, rcon);
        //System.out.println(Byte.toUnsignedInt(rcon));
    } /* aes256_init */

    public void aes256_done(AES256Context ctx) {
        byte i;

        for (i = 0; i < 32; i++)
            ctx.key[i] = ctx.enkey[i] = ctx.deckey[i] = 0;
    } /* aes256_done */

    public void aes256_encrypt_ecb(AES256Context ctx, byte[] buf) {
        byte i, rcon;

        aes_addRoundKey_cpy(buf, ctx.enkey, ctx.key);
        for (i = 1, rcon = 1; i < 14; ++i) {
            aes_subBytes(buf);
            aes_shiftRows(buf);
            aes_mixColumns(buf);
            if ((i & 1) != 0)
                aes_addRoundKey(buf, ctx.key, 16);
            else {
                rcon = aes_expandEncKey(ctx.key, rcon);
                //System.out.println(Byte.toUnsignedInt(rcon));
                aes_addRoundKey(buf, ctx.key);
            }
        }
        aes_subBytes(buf);
        aes_shiftRows(buf);
        rcon = aes_expandEncKey(ctx.key, rcon);
        aes_addRoundKey(buf, ctx.key);
    } /* aes256_encrypt */

    public void aes256_decrypt_ecb(AES256Context ctx, byte[] buf) {
        byte i, rcon;

        aes_addRoundKey_cpy(buf, ctx.deckey, ctx.key);
        aes_shiftRows_inv(buf);
        aes_subBytes_inv(buf);

        for (i = 14, rcon = (byte) 0x80; --i > 0; ) {
            if ((i & 1) != 0) {
                rcon = aes_expandDecKey(ctx.key, rcon);
                aes_addRoundKey(buf, ctx.key, 16);
            } else
                aes_addRoundKey(buf, ctx.key);
            aes_mixColumns_inv(buf);
            aes_shiftRows_inv(buf);
            aes_subBytes_inv(buf);
        }
        aes_addRoundKey(buf, ctx.key);
    } /* aes256_decrypt */
}

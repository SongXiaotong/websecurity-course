public class MD5 {
	static final String hexs[]={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    private static final long A=0x67452301L;
    private static final long B=0xefcdab89L;
    private static final long C=0x98badcfeL;
	private static final long D=0x10325476L;
	private static long[] res = {A, B, C, D};
	private static long[] deal = {A, B, C, D};
	private static final int s[][] = {
		{7,12,17,22,7,12,17,22,7,12,17,22,7,12,17,22},
		{5,9,14,20,5,9,14,20,5,9,14,20,5,9,14,20}, 
		{4,11,16,23,4,11,16,23,4,11,16,23,4,11,16,23}, 
		{6,10,15,21,6,10,15,21,6,10,15,21,6,10,15,21}
	};
	private static final long T[] = {
		0xd76aa478L, 0xe8c7b756L, 0x242070dbL, 0xc1bdceeeL, 0xf57c0fafL, 0x4787c62aL, 0xa8304613L, 0xfd469501L, 0x698098d8L, 0x8b44f7afL, 0xffff5bb1L, 0x895cd7beL, 0x6b901122L, 0xfd987193L, 0xa679438eL, 0x49b40821L,
		0xf61e2562L, 0xc040b340L, 0x265e5a51L, 0xe9b6c7aaL, 0xd62f105dL, 0x02441453L, 0xd8a1e681L, 0xe7d3fbc8L, 0x21e1cde6L, 0xc33707d6L, 0xf4d50d87L, 0x455a14edL, 0xa9e3e905L, 0xfcefa3f8L, 0x676f02d9L, 0x8d2a4c8aL,
		0xfffa3942L, 0x8771f681L, 0x6d9d6122L, 0xfde5380cL, 0xa4beea44L, 0x4bdecfa9L, 0xf6bb4b60L, 0xbebfbc70L, 0x289b7ec6L, 0xeaa127faL, 0xd4ef3085L, 0x04881d05L, 0xd9d4d039L, 0xe6db99e5L, 0x1fa27cf8L, 0xc4ac5665L,
		0xf4292244L, 0x432aff97L, 0xab9423a7L, 0xfc93a039L, 0x655b59c3L, 0x8f0ccc92L, 0xffeff47dL, 0x85845dd1L, 0x6fa87e4fL, 0xfe2ce6e0L, 0xa3014314L, 0x4e0811a1L, 0xf7537e82L, 0xbd3af235L, 0x2ad7d2bbL, 0xeb86d391L};
	public static void main(String []args){
        MD5 md=new MD5();
		String test = "123456";
		byte[] allBytes = changeToBytes(test);
		long[][] groups = changeToLongGroups(allBytes);
		///System.out.println(groups.length);
		for(int t = 0; t < groups.length; ++t){
			recycle(groups[t]);
			
			String resStr="";
        	long temp=0;
       		for(int i=0;i<4;i++){
            	for(int j=0;j<4;j++){
					temp=res[i]&0x0FL;
					String a=hexs[(int)(temp)];
					res[i]=res[i]>>4;
					temp=res[i]&0x0FL;
					resStr+=hexs[(int)(temp)]+a;
					res[i]=res[i]>>4;
				}
			}
			System.out.println(resStr+"\n");
		}
    }
	
	private static byte[] changeToBytes(String str) {
		int K = str.length() * 8;
		int P = K % 512;
		P = P < 448 ? 448-P : 512-P+448;
	    byte[] allBytes = new byte[K/8+P/8+8];
	    for(int i = 0; i < K/8; ++i) 
	    	allBytes[i] = str.getBytes()[i];
	    for(int i = 0; i < P/8; ++i) 
			allBytes[K/8 + i] = (i == 0) ? (byte)128 : (byte)0;
			
		long len=(long)(K/8<<3);
            for(int i=0;i<8;i++){
                allBytes[K/8 + P/8 +i]=(byte)(len&0xFFL);
                len=len>>8;
        }
	    // if(K >= 64) 
	    // 	for(int i = 0; i < 8; ++i)	
	    // 		allBytes[K/8+P/8+i] = allBytes[K/8-8+i];
	    // else {
	    // 	for(int i = 0; i < 8; ++i) {
	    // 		if((i+K/8) < 8) allBytes[K/8+P/8+i] = 0 & 0xff;
	    // 		else allBytes[K/8+P/8+i] = allBytes[i+K/8-8];
	    // 	}
		// }
		
	    return allBytes;
	}
	
	private static long[][] changeToLongGroups(byte[] allBytes) {
	
		int groupNum = allBytes.length / 64;
		
		long[][] res = new long[groupNum][16];
		for(int i = 0; i < groupNum; ++i) {
			for(int j=0; j<16; ++j){
	            res[i][j] = (offSigned(allBytes[64*i + j*4])) |
					(offSigned(allBytes[64*i + j*4 + 1])) << 8|
					(offSigned(allBytes[64*i + j*4 + 2])) << 16|
					(offSigned(allBytes[64*i + j*4 + 3])) << 24;
				
	        }
		}
		
		return res;
	}

	
	private static void recycle(long[] group){
		// System.out.println("1");
		for(int i = 0; i < 4; ++i){
			for(int j = 0; j < 16; ++j){
				int k = changeK(i, j);
				if(j%4 == 0)
					deal[0] = funcPoint(deal[0], deal[1], deal[2], deal[3], group[k], s[i][j], T[i*16+j], i);
				else if(j%4 == 1)
					deal[3] = funcPoint(deal[3], deal[0], deal[1], deal[2], group[k], s[i][j], T[i*16+j], i);
				else if(j%4 == 2)
					deal[2] = funcPoint(deal[2], deal[3], deal[0], deal[1], group[k], s[i][j], T[i*16+j], i);
				else
					deal[1] = funcPoint(deal[1], deal[2], deal[3], deal[0], group[k], s[i][j], T[i*16+j], i);
				
			}
				
		}

        res[0] = (deal[0] + res[0]) & 0xFFFFFFFFL;
		res[1] = (deal[1] + res[1]) & 0xFFFFFFFFL;
		res[2] = (deal[2] + res[2]) & 0xFFFFFFFFL;
		res[3] = (deal[3] + res[3]) & 0xFFFFFFFFL;
	}

	private static long funcPoint(long a, long b, long c, long d, long Mj, long s, long ti, int choose){
		if(choose == 0)
			return FF(a, b, c, d, Mj, s, ti);
		else if(choose == 1)
			return GG(a, b, c, d, Mj, s, ti);
		else if(choose == 2)
			return HH(a, b, c, d, Mj, s, ti);
		else
			return II(a, b, c, d, Mj, s, ti);
	}

	public static long offSigned(byte bytes){
        return bytes < 0 ? bytes & 0x7F + 128 : bytes;
    }
	
	private static long F(long X, long Y, long Z) {
		return (X & Y) | ((~X) & Z);
	}
	private static long G(long X, long Y, long Z) {
		return (X & Z) | (Y & (~Z));
	}
	private static long H(long X, long Y, long Z) {
		return X^Y^Z;
	}
	private static long I(long X, long Y, long Z) {
		return Y ^ (X | (~Z));
	}
	private static long FF(long a, long b, long c, long d, long Mj, long s, long ti) {
		a += (F(b, c, d) & 0xFFFFFFFFL) + Mj + ti;
        a = ((a&0xFFFFFFFFL)<< s) | ((a&0xFFFFFFFFL) >> (32 - s));
        a += b;
		return (a & 0xFFFFFFFFL);
	}
	private static long GG(long a, long b, long c, long d, long Mj, long s, long ti) {
		a += (G(b, c, d) & 0xFFFFFFFFL) + Mj + ti;
        a = ((a&0xFFFFFFFFL)<< s) | ((a&0xFFFFFFFFL) >> (32 - s));
        a += b;
		return (a & 0xFFFFFFFFL);
	}
	private static long HH(long a, long b, long c, long d, long Mj, long s, long ti) {
		a += (H(b, c, d) & 0xFFFFFFFFL) + Mj + ti;
        a = ((a&0xFFFFFFFFL)<< s) | ((a&0xFFFFFFFFL) >> (32 - s));
        a += b;
		return (a & 0xFFFFFFFFL);
	}
	private static long II(long a, long b, long c, long d, long Mj, long s, long ti) {
		a += (I(b, c, d) & 0xFFFFFFFFL) + Mj + ti;
        a = ((a&0xFFFFFFFFL)<< s) | ((a&0xFFFFFFFFL) >> (32 - s));
        a += b;
		return (a & 0xFFFFFFFFL);
	}

	private static int changeK(int i, int j){
		int k = 0;
		switch(i){
			case 0:
				k = j;
				break;
			case 1:
				k = (1 + 5 * j) % 16;
				break;
			case 2:
				k = (5 + 3 * j) % 16;
				break;
			default:
				k = (7 * j) % 16;
				break;
		}
		return k;
	}

}

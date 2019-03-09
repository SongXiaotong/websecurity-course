import operator
import rule

def transforming(data, trans, len):
    res = ['' for j in range(len)]
    for i in range(len):
        res[i] = data[trans[i]-1]
    return res

# 置换并分解
# data----要处理的数据
# trans---置换规则
# len-----新得到的二进制位数
def tran_half(data, trans, len):
    res = [['' for i in range(0, len//2)] for i in range(0, 2)]
    for i in range(0, 2):
        for j in range(0, len//2):
            res[i][j] = data[trans[i*(len//2)+j]-1]
    return res[0], res[1]

def shift(data, num):
    res = ['' for i in range(len(data))]
    for i in range(len(data)):
        if i < len(data)-num:
            res[i] = data[i+num]
        else:
            res[i] = data[i+num-len(data)]
    return res;
            
# 产生16个左右两部分输出
# c0 d0-----第一次分解密钥的两部分
# left------左移规则
# bit-------左右两部分的大小（32）
# len-------循环次数
def shift_16(c0, d0, left, bit, len):
    c = [['' for i in range(bit)] for i in range(len+1)]
    d = [['' for i in range(bit)] for i in range(len+1)]
    c[0] = c0;
    d[0] = d0;
    for i in range(1, len+1): 
        c[i] = shift(c[i-1], left[i-1])
        d[i] = shift(d[i-1], left[i-1])
    return c, d

# 产生16个子密钥
# c d-------左右两部分的列表
# num-------循环次数
# trans-----置换规则
# len-------子密钥位数
def key_16(c, d, num, trans, len):
    k = [['' for i in range(len)] for i in range(num)]
    for i in range(num):
        k[i] = transforming(c[i+1]+d[i+1], trans, len)
    return k

def exclusive(data1, data2, len):
    res = ['' for i in range(len)]
    for i in range(len):
        if operator.eq(data1[i], data2[i]):
            res[i] = '0'
        else:
            res[i] = '1'
    return res


# s盒选择
# data-------处理数据
# s----------s盒的列表，元素仍为列表
# inputlen, output_len-------输入6位，输出4位
# times------操作次数8

def s_box(data, s, input_len, output_len, times):
    res = ['' for i in range(output_len * times)]
    for i in range(times):
        x = int(data[i*input_len])*2 + int(data[i*input_len+5])
        y = int(data[i*input_len+1])*8 + int(data[i*input_len+2])*4 + int(data[i*input_len+3])*2 + int(data[i*input_len+4])
        binary =  bin(s[i][x*16 + y])
        for j in range(4):
            if len(binary)-1-j >= 0:
                if binary[len(binary)-1-j] != 'b':
                    res[(i+1)*output_len-1-j] = binary[len(binary)-1-j]
                else:
                    res[(i+1)*output_len-1-j] = '0'
            else:
                res[(i+1)*output_len-1-j] = '0'
    return res
        

# 轮函数
# l r-------一轮循环被操作的左右两部分
# k---------当前要用的单个子密钥
# ex_len----扩展位数48
# s---------整个s盒
# len-------输出位数

def recycle(l, r, k, ex_len, s, len):
    res = [['' for i in range(len)] for i in range(2)]
    res[0] = r
    ex_r_k = exclusive(transforming(r, rule.E_Trans, ex_len), k, ex_len)
    s_32 = s_box(ex_r_k, s, 6, 4, 8)
    p_32 = transforming(s_32, rule.P, 32)
    res[1] = exclusive(l, p_32, 32)
    return res


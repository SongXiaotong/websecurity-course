import rule
import function
import operator

print("\n\nuse the example to test the programme")

print("--------------------------------------------------------")
# input the data
data = "0000000100100011010001010110011110001001101010111100110111101111"
# data = input("please input the data(64): ")
#input the key
key = "0001001100110100010101110111100110011011101111001101111111110001"
#key  = input("please input the key (64): ")
#input the mode
#mode = input("please input the mode(01): ")
expect = "1000010111101000000100110101010000001111000010101011010000000101"
# set l0 and r0
lr = function.tran_half(data, rule.ip, 64)
# set c0 and d0
c0, d0 = function.tran_half(key, rule.pc_1, 56)
# left shift the cds
c = [['' for i in range(28)] for i in range(17)]
d = [['' for i in range(28)] for i in range(17)]
c, d = function.shift_16(c0, d0, rule.left_shift, 28, 16)
# 16 key[0-15]
k = function.key_16(c, d, 16, rule.PC_2, 48)

#16 operations
for r in range(16):
    lr = function.recycle(lr[0], lr[1], k[r], 48, rule.S, 32)
#change l and r then do the transform
res = function.transforming(lr[1]+lr[0], rule._ip, 64)
print("The expected result of example is: " + expect)
print("The ciphertext of the same data is:" + ''.join(res))
if operator.eq(expect, ''.join(res)):
    print("Get the result successfully!\n")

lr = function.tran_half(res, rule.ip, 64)
for r in range(16):
    lr = function.recycle(lr[0], lr[1], k[15-r], 48, rule.S, 32)
res = function.transforming(lr[1]+lr[0], rule._ip, 64)
print("The original input data is:" + data)
print("The checking reference is: " + ''.join(res))
if operator.eq(''.join(res), data):
    print("Return to the data successfully!")

print("--------------------------------------------------------")


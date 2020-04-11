---
layout: single 
title:  "Strassen Algorithm" 
date:  2020-04-06 16:52:00 +0900 
tags:
  - "github"
  - "algorithm"
author:  Kim Hong jun
use_math: true
---



# 스트라센 알고리즘(Strassen)

### <u>1. 개요</u><br>

![](https://images.unsplash.com/photo-1518133910546-b6c2fb7d79e3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjExNzczfQ&s=b853ed7d370a7d7c9484138375d77cc3)

행렬 연산에서 크기가 커지면 곱셈 연산에서 더 많은 시간을 소요한다. 이것을 이용하여 행렬곱 연산에서 곱셈을 줄이고 덧셈을 늘림으로써 연산시간을 줄인 알고리즘이다.

### <u>2. 알고리즘 방법</u><Br>



2x2 행렬을 예로 들면 행렬 A와 B가 있고 두 행렬을 곱한 행렬이 C라고 가정한다.<br>


$$
A = \begin{bmatrix}
a_{11} & a_{12}\\
a_{21} & a_{22}
\end{bmatrix}
B =\begin{bmatrix}
b_{11} & b_{12}\\
b_{21} & b_{22}
\end{bmatrix}  
C =\begin{bmatrix}
c_{11} & c_{12}\\
c_{21} & c_{22}
\end{bmatrix}
$$
<Br>

이 행렬곱을 일반적으로 계산하면<br>
$$
c_{11} = a_{11}b_{11} + a_{12}b_{21}\\
c_{12} = a_{11}b_{12} + a_{12}b_{22}\\
c_{21} = a_{21}b_{11} + a_{22}b_{21}\\
c_{22} = a_{21}b_{12} + a_{22}b_{22}
$$
<br>이 된다.

=> __총 <u>8번의 곱셉</u>과 <u>4번의 덧셈</u>으로 연산 가능하다.__

<br>

스트라센에서 행렬 곱셈을 더하기 연산으로 풀어서 각 원소를 구할 수 있는 M이라는 연산 행렬로 표현하면<br>

<br>
$$
M_1 = (a_{11}+a_{22})(b_{11}+b_{22})\\
M_2 = (a_{21}+a_{22})b_{11}\\
M_3 = a_{11}(b_{12}-b_{22})\\
M_4 = a_{22}(b_{21} - b_{11})\\
M_5 = (a_{11}+a_{12})b_{22}\\
M_6 = (a_{21}-a_{11})(b_{11}+b_{12})\\
M_7 = (a_{12}-a_{22})(b_{21}+b_{22})
$$
<Br>이 된다.<Br>

<br> __이 행렬은 7번의 곱셉과 10번의 덧셈 연산으로 나타낼 수 있고__ 최종적으로는

<br>
$$
c_{11} = M_1+M_4+M_5+M_7\\
c_{12} = M_3 + M_5\\
c_{21} = M_2 + M_4\\
c_{22} = M_1-M_2+M_3+M_6
$$
<Br>이 되어 __7번의 곱셈과 18번(10+8)의 덧셈으로 처리할 수 있다.__

<br>

### <u>3. 시간 복잡도</u><br>

nxn 행렬을 곱하면 시간복잡도가 $ O(n^3) $ 가 되지만 스트라센 알고리즘을 사용하게 되면 대략 $ O(n^{2.807}) $이 된다.  <br>=>  큰 행렬에 대해서는 곱셈이 덧셈보다 더 많은 시간을 필요로 하기 때문에 덧셈을 더 하는 대신 곱셈을 덜 하는 것이 효율적이다.

<br>



### <u>4. 코드</u> <br>

직접 코드를 작성하기에는 어려움이 있어서 코드를 가져와서 분석해 보았다.

- getMatrix : 행렬을 만드는 함수이다.<br>
- partitionMatrix : 행렬을 나누는 함수같은데 정확하게는 이해가 안된다..<br>
- strassen : 스트라센 알고리즘으로 행렬을 곱하는 함수이다.<br>
- matrixMul : 일반적인 행렬곱을 하는 함수이다.<br>
- strassenTimeFunction : 스트라센 알고리즘 시간을 측정하는 함수이다.<br>
- mulTimeFunction : 일반적인 행렬곱 시간을 측정하는 함수이다.<br>



```python
import numpy as np
import time
import matplotlib.pyplot as plt
import math
from random import *

def genMatrix(n):
    newMarix = []
    for i in range(0,n):
        dataline = []
        for j in range(0,n):
            dataline.append(10)

        for j in range(0, n):
            Vals = list(map(float, dataline))

        newMarix.append(Vals)
    return newMarix

def partitionMatrix(matrix):
    length = len(matrix)
    if(length % 2 is not 0):
        stack = []
        for x in range(length + 1):
            stack.append(float(0))
        length += 1
        matrix = np.insert(matrix, len(matrix), values=0, axis=1)
        matrix = np.vstack([matrix, stack])
    d = (length // 2)
    matrix = matrix.reshape(length, length)
    completedPartition = [matrix[:d, :d], matrix[d:, :d], matrix[:d, d:], matrix[d:, d:]]
    return completedPartition

def strassen(mA, mB,aN):
    n1 = len(mA)
    n2 = len(mB)
    # global aN
    if(n1 and n2 <= aN):
        return (mA * mB)
    else:
        print(mA)
        A = partitionMatrix(mA)
        B = partitionMatrix(mB)
        mc = np.matrix([0 for i in range(len(mA))]for j in range(len(mB)))
        C = partitionMatrix(mc)

        a11 = np.array(A[0])
        a12 = np.array(A[2])
        a21 = np.array(A[1])
        a22 = np.array(A[3])

        b11 = np.array(B[0])
        b12 = np.array(B[2])
        b21 = np.array(B[1])
        b22 = np.array(B[3])

        mone = np.array(strassen((a11 + a22), (b11 + b22)))
        mtwo = np.array(strassen((a21 + a22), b11))
        mthree = np.array(strassen(a11, (b12 - b22)))
        mfour = np.array(strassen(a22, (b21 - b11)))
        mfive = np.array(strassen((a11 + a12), b22))
        msix = np.array(strassen((a21 - a11), (b11 + b12)))
        mseven = np.array(strassen((a12 - a22), (b21 + b22)))

        C[0] = np.array((mone + mfour - mfive + mseven))
        C[2] = np.array((mthree + mfive))
        C[1] = np.array((mtwo + mfour))
        C[3] = np.array((mone - mtwo + mthree + msix))

        return np.array(C)


#단순행렬곱
def matrixMul(mA, mB):
    resMatirx = []
    A = np.array(mA)
    B = np.array(mB)
    n = len(A)

    for i in range(0,n):
        dataline = []
        for j in range(0,n):
            sum = 0
            for k in range(0,n):
                sum += A[i][k]*B[k][j]
            dataline.append(sum)
        resMatirx.append(dataline)

    return resMatirx

#strassen 시간측정
def strassenTimeFunction(N):
    aN = bN = N

    matrixA = genMatrix(aN)
    matrixB = genMatrix(bN)
    matrixA = np.matrix(matrixA)
    matrixB = np.matrix(matrixB)

    startStrassen = time.time()
    matrixC = [[0 for i in range(len(matrixA))] for j in range(len(matrixA))]
    matrixC = strassen(matrixA, matrixB,N)
    endStrassen = time.time()
    strassenTime = endStrassen - startStrassen
    print(matrixC)
    return strassenTime

#단순곱 시간측정
def mulTimeFunction(N):
    aN = bN = N

    matrixA = genMatrix(aN)
    matrixB = genMatrix(bN)
    matrixA = np.matrix(matrixA)
    matrixB = np.matrix(matrixB)

    startMul = time.time()
    matrixD = matrixMul(matrixA, matrixB)
    endMul = time.time()
    mulTime = endMul - startMul
    print(matrixD,end=']')
    return mulTime


#시간체크
N = int(input('N = '))
N = pow(2, N)
strassenTime = strassenTimeFunction(N)
mulTime = mulTimeFunction(N)
print("strassen time = %lf" %strassenTime)
print("mul time = %lf" %mulTime)

```

<br>

### <u>5. 실행결과</u>



![N=7 일 때](https://github.com/KIMHONGJUN2/KIMHONGJUN2.github.io/blob/master/assets/picture/strassen%20n=7.png?raw=true)

N = 7 이므로 $ 2^7 =128 $, 128x128 행렬곱을 한 결과이다.<br>

스트라센 알고리즘은 0.000995 초가 걸렸고 <br>

일반 행렬곱은 1.415911 초가 걸렸다.

![](https://github.com/KIMHONGJUN2/KIMHONGJUN2.github.io/blob/master/assets/picture/strassen%20n=9.png?raw=true)

N = 9 이므로 $ 2^9 = 512 $, 512x512 행렬곱을 한 결과이다.

스트라센 알고리즘은 0.009958 초가 걸렸고<br>

일반 행렬곱은 101.741784 초가 걸렸다.<br>

시간 복잡도로 보면 $ O(n^3) $ 와  $ O(n^{2.807}) $로 큰 차이가 나지 않는 것처럼 보였는데 프로그램으로 확인해서 보니 숫자가 커질수록 시간차이가 점점 커져서 차이를 실감할 수 있었다.
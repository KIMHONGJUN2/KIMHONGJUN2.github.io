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

선형대수학에서 `폴커 슈트라센` 이 1969년에 개발한 행렬 곱셈 알고리즘이다. 행렬의 곱연산을 할 때 행렬이 커지면 커질수록 연산 속도가 기하급수적으로 증가하게 되는데 cpu 구조상 더하기 연산이 빠르기 때문에 스트라센 알고리즘에서는 곱하기 연산을 더하기 연산으로 치환하여 계산하도록 보안했다.

### <u>2. 알고리즘 방법</u><Br>



먼저 행렬 A와 B가 있고 두 행렬을 곱한 행렬이 C라고 가정한다.<br>


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





```python
import numpy as np
def strassen_multiplication(a, b, n):
    n1, n2 = a.shape
    if n != n1 or n != n2:
        print("Only square matrices are acceptted. Please pad your matrices.")
        return 0
    elif n % 2 != 0:
        print("Only even matrices are acceptted. Please pad your matrices.")
        return 0

    if n == 2:
        m1 = (a[0][0] + a[1][1]) * (b[0][0] + b[1][1])
        m2 = (a[1][0] + a[1][1]) * b[0][0]
        m3 = a[0][0] * (b[0][1] - b[1][1])
        m4 = a[1][1] * (b[1][0] - b[0][0])
        m5 = (a[0][0] + a[0][1]) * b[1][1]
        m6 = (a[1][0] - a[0][0]) * (b[0][0] + b[0][1])
        m7 = (a[0][1] - a[1][1]) * (b[1][0] + b[1][1])

        c11 = m1 + m4 - m5 + m7
        c12 = m3 + m5
        c21 = m2 + m4
        c22 = m1 - m2 + m3 + m6

        a = np.stack((c11, c21), axis=0)
        b = np.stack((c12, c22), axis=0)
        c = np.stack((a, b), axis=1)

        return c

    a11 = a[0:n // 2, 0:n // 2]
    a12 = a[0:n // 2, n // 2:n]
    a21 = a[n // 2:n, 0:n // 2]
    a22 = a[n // 2:n, n // 2:n]

    b11 = b[0:n // 2, 0:n // 2]
    b12 = b[0:n // 2, n // 2:n]
    b21 = b[n // 2:n, 0:n // 2]
    b22 = b[n // 2:n, n // 2:n]

    m1 = strassen_multiplication(a11 + a22, b11 + b22, n // 2)
    m2 = strassen_multiplication(a21 + a22, b11, n // 2)
    m3 = strassen_multiplication(a11, b12 - b22, n // 2)
    m4 = strassen_multiplication(a22, b21 - b11, n // 2)
    m5 = strassen_multiplication(a11 + a12, b22, n // 2)
    m6 = strassen_multiplication(a21 - a11, b11 + b12, n // 2)
    m7 = strassen_multiplication(a12 - a22, b21 + b22, n // 2)

    c11 = m1 + m4 - m5 + m7
    c12 = m3 + m5
    c21 = m2 + m4
    c22 = m1 - m2 + m3 + m6

    a = np.concatenate((c11, c12), axis=1)
    b = np.concatenate((c21, c22), axis=1)
    c = np.concatenate((a, b), axis=0)

    return c


n = 2              #2x2행렬 생성

a = np.zeros((n, n))
k = 0           
for i in range(n):
    for j in range(n):
        a[i, j] = k
        k += 1           #a행렬에 0부터 3까지 숫자로 채운다.

b = np.zeros((n, n))
for i in range(n):
    for j in range(n):
        b[i, j] = k 
        k += 1          #b행렬에 4부터 7까지 숫자로 채운다.



c = a.dot(b)
print(a)  #a행렬 출력
print(b)  #b행렬 출력
print(c)  #c행렬(axb)

c = strassen_multiplication(a, b, n)
print("Result:"))
print(c)  #스트라센으로 계산한 c행렬 출력
```

[참고](https://github.com/jjbits/AlgorithmicBits/blob/19e5ebd289f014e5b668dc37bfabd2a80d339a63/matrix/matrix.py)


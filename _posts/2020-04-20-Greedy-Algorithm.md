---
layout: single 
title:  "Greedy Algorithm" 
date:  2020-04-20 16:52:00 +0900 
tags:
  - "github"
  - "algorithm"
author:  Kim Hong jun
use_math: true
---

# Greedy Algorithm - 버스 타기🚌

<u> 2018 삼성 대학생 프로그래밍 경진대회(SCPC)</u> 1차 예선에 출제되었던 문제 중 그리디 알고리즘과 관련된 문제를 찾게 되어 조사해보았다.

#### 🕐문제

- n명의 바둑 선수가 몇 대의 버스에 나누어 타려고 한다.
- 각 선수는 실력 값을 가지고 있다.
- 실력 값이 k 이하로 차이나는 선수끼리는 같은 버스를 탈 수 없다.
- __선수의 숫자 n__ 은 1 <= n <= 200,000 이고  __실력 차이 k__ 는 1<= k <= 1,000,000,000이다.<br>

#### => 선수들의 실력을 입력 받았을 때 필요한 버스 수의 최소 값을 구하는 알고리즘 구하기<br><br>

<u>예제 입력</u>

```
3          -> 테스트 케이스 수
1 1        -> 첫번째 케이스 인원수n과 실력 값 k
2          -> 선수 실력값 (1명)
2 3        -> 두번째 케이스 n=2, k=3
1 4        -> 선수들의 실력값(2명) 
5 3        -> 세번째 케이스 n=5, k=3
1 5 3 7 9  -> 선수들의 실력값(5명)
```

<u>예제 출력</u>

```
Case#1
1
Case#2
2
Case#3
2
```

<br>

#### 🕑 알고리즘

예) n=6이고 k=3이고 1, 4, 3, 7, 5, 9 가 선수들 각각의 실력 값이라고 가정한다.

먼저 실력 값들을 정렬한다.

1, 4, 3, 7, 5, 9 => 1, 3, 4, 5, 7, 9

- 처음 값인 1일 읽는다.     -   __버스 1대__
- 1, 3 까지 읽었을 때 1과 3을 비교한다. 실력차가 3(k)보다 작다.     -  __버스 2대__

- 1, 3, 4 까지 읽었을 때 버스가 2대 필요하다는 것을 알고 있으므로 2칸 뒤로 가서 1과 4를 비교한다. 실력차(k)가 3이하이므로 버스를 추가한다.     -  __버스 3대__ 
- 1, 3, 4, 5 까지 읽었을 때 버스 3대가 필요한 것을 알고 있으므로 3칸 뒤로 가서 1과 5를 비교한다. 이번에는 실력차(k)가 3보다 크기 때문에 그대로 유지한다.   -  __버스 3대__
- 1, 3, 4, 5, 7 까지 읽었을 때 버스 3대가 필요한 것을 알고 있기 때문에 3칸 뒤로 가서 3과 7을 비교한다. 실력차(k)가 3보다 크므로 유지한다.         -   __버스 3대__
- 1, 3, 4, 5, 7, 9 까지 읽었을 때 버스 3대가 필요한 것을 알고 있으므로 3칸 뒤로 이동 후 4와 9를 비교한다. 실력차(k)가 3보다 크므로 유지한다.     -   __버스 3대__

<br>



#### 🕒 코드

c++로 작성된 코드가 있어서 참고해서 JAVA로 수정 후 실행시켜 보았다.

```java
import java.util.Arrays;
import java.util.Scanner;

public class Bus {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();     // testcase 수 입력
        int testcase = 0;
        int bus = 0;
        int[] alist = new int [t+1];
		
        // testcase 수 만큼 반복 
        for (testcase = 1; testcase <= t; testcase++) {
            bus = 1;         // 처음 버스 1대로 설정
            int n=0,k=0;
            n=sc.nextInt();     // 인원 수(n) 와 실력값 차이(k) 입력
            k=sc.nextInt();
            int[] a  = new int[n];
       
            // 선수들의 실력값 입력
            for (int i = 0; i <n ; i++) {
                int tmp;
                tmp = sc.nextInt();
                a[i] = tmp;
            }
            // 실력값 오름차순으로 정렬
            Arrays.sort(a);
            
            // 실력값을 비교해서 차이가 k보다 작으면 버스 추가
            for (int j = 1; j <n; j++) {
                if(a[j]-a[j-answer]<=k){
                   bus++;
                }

            }
            // testcase 별로 버스 수 저장
            alist[testcase] = bus;
        }
        //testcase 별로 버스 수 출력
        for (int i = 1; i <= t; i++) {
            System.out.println("Case #"+i);
            System.out.println(alist[i]);

        }
    }
}

```

<br>

#### 🕓실행 화면

![](https://github.com/KIMHONGJUN2/KIMHONGJUN2.github.io/blob/master/assets/picture/greedy_bus.png?raw=true)




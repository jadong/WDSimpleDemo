package com.example.dong

println "This is hello "

def repeat(val, repeat = 3) {
    for (i in 0..<repeat) {
        println "This is ${i}:${val}"
    }
}

repeat(5)

def map = ['name': 'dong', 'age': 18, 'sex': 'boy']

map.each({ key, value ->
    println("$key---$value")
})
println("======================")

map.each {
    println(it)
}

def say = { word ->
    println("this is : $word")
}

say("dong")


//IP的权重分别为:
//192.168.1.101 权重9
//192.168.1.102 权重5
//192.168.1.103 权重4
//192.168.1.104 权重6
//192.168.1.105 权重8

//有1501个请求按权重分别给不同ip发送请求


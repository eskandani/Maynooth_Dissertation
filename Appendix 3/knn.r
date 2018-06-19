library(class)
library(readxl)
library(nnet)
setwd("~/Desktop/")
trainset <- read.csv("Melanie.csv", header = T, na.strings = c("", "NA"))
testdata <- read.csv("Melanie2.csv", header=T, na.strings=c("","NA"))
a <- trainset$ST
b <- trainset$HF
c <- trainset$GSR
d <- as.factor(trainset$Memorable)
e <- as.factor(trainset$Important.that.Time)
f <- as.factor(trainset$Important.Now)
g <- as.factor(trainset$Remember)
h <- as.factor(trainset$Feel.that.Time)
i <- as.factor(trainset$Feel.Re.Presented)
j <- as.factor(trainset$Retrieve.the.Event)
act <- as.factor(trainset$Activity)

a_ <- c(testdata$ST)
b_ <- c(testdata$HF)
c_ <- c(testdata$GSR)

train <- cbind(a,b,c)
test <- cbind(a_,b_,c_)

dRes = knn(train = train , test = test, cl = d, k = 3, prob = T)
eRes = knn(train = train , test = test, cl = e, k = 3, prob = T)
fRes = knn(train = train , test = test, cl = f, k = 3, prob = T)
gRes = knn(train = train , test = test, cl = g, k = 3, prob = T)
hRes = knn(train = train , test = test, cl = h, k = 3, prob = T)
iRes = knn(train = train , test = test, cl = i, k = 3, prob = T)
jRes = knn(train = train , test = test, cl = j, k = 3, prob = T)
actRes = knn(train = train , test = test, cl = act, k = 3, prob = T)
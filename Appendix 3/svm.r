library(e1071)
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

modelSVMD <- svm(d ~ a + b + c, trainset, probability = TRUE)
modelSVME <- svm(e ~ a + b + c, trainset, probability = TRUE)
modelSVMF <- svm(f ~ a + b + c, trainset, probability = TRUE)
modelSVMG <- svm(g ~ a + b + c, trainset, probability = TRUE)
modelSVMH <- svm(h ~ a + b + c, trainset, probability = TRUE)
modelSVMI <- svm(i ~ a + b + c, trainset, probability = TRUE)
modelSVMJ <- svm(j ~ a + b + c, trainset, probability = TRUE)
modelSVMAct <- svm(act ~ a + b + c, trainset, probability = TRUE)

a = testdata$ST; b = testdata$HF; c = testdata$GSR;
mytestdata = data.frame(a,b,c)

predSVMD <- predict(modelSVMD, mytestdata, decision.values = TRUE,probability = TRUE)
predSVME <- predict(modelSVME, mytestdata, decision.values = TRUE,probability = TRUE)
predSVMF <- predict(modelSVMF, mytestdata, decision.values = TRUE,probability = TRUE)
predSVMG <- predict(modelSVMG, mytestdata, decision.values = TRUE,probability = TRUE)
predSVMH <- predict(modelSVMH, mytestdata, decision.values = TRUE,probability = TRUE)
predSVMI <- predict(modelSVMI, mytestdata, decision.values = TRUE,probability = TRUE)
predSVMJ <- predict(modelSVMJ, mytestdata, decision.values = TRUE,probability = TRUE)
predSVMAct <- predict(modelSVMAct, mytestdata, decision.values = TRUE,probability = TRUE)
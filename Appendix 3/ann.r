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

modelD <- multinom(d ~ a + b + c, trainset)
modelE <- multinom(e ~ a + b + c, trainset)
modelF <- multinom(f ~ a + b + c, trainset)
modelG <- multinom(g ~ a + b + c, trainset)
modelH <- multinom(h ~ a + b + c, trainset)
modelI <- multinom(i ~ a + b + c, trainset)
modelJ <- multinom(j ~ a + b + c, trainset)
modelAct <- multinom(act ~ a + b + c, trainset)

a = testdata$ST; b = testdata$HF; c = testdata$GSR;
mytestdata = data.frame(a,b,c)


predictMNL <- function(model, newdata) {
  
  if (is.element("nnet",class(model))) {
    probs <- predict(model,newdata,"probs")
    cum.probs <- t(apply(probs,1,cumsum))
    
    vals <- runif(nrow(newdata))
    
    tmp <- cbind(cum.probs,vals)
    
    k <- ncol(probs)
    ids <- 1 + apply(tmp,1,function(x) length(which(x[1:k] < x[k+1])))
    
    return(ids)
  }
}

predD <- predictMNL(modelD, mytestdata)
predE <- predictMNL(modelE, mytestdata)
predF <- predictMNL(modelF, mytestdata)
predG <- predictMNL(modelG, mytestdata)
predH <- predictMNL(modelH, mytestdata)
predI <- predictMNL(modelI, mytestdata)
predJ <- predictMNL(modelJ, mytestdata)
predAct <- predictMNL(modelAct, mytestdata)

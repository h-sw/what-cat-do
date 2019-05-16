import cv2
import glob
import os
from PIL import Image

globpath = 'C:\\Users\\cvj98\\Desktop\\Emo\\Angry\\*.jpg'
print(globpath)
images = glob.glob(globpath)

for ff in images :
    name = '_'+ff[33:]
    img = Image.open(ff)
    path = 'C:\\Users\\cvj98\\Desktop\\Emo\\Angry\\'+name
    print(path)
    img.convert('L').save(path)

globpath = 'C:\\Users\\cvj98\\Desktop\\Emo\\curious\\*.jpg'
print(globpath)
images = glob.glob(globpath)

for ff in images :
    name = '_'+ff[34:]
    img = Image.open(ff)
    path = 'C:\\Users\\cvj98\\Desktop\\Emo\\curious\\'+name
    print(path)
    img.convert('L').save(path)

globpath = 'C:\\Users\\cvj98\\Desktop\\Emo\\Excitied\\*.jpg'
print(globpath)
images = glob.glob(globpath)

for ff in images :
    name = '_'+ff[36:]
    img = Image.open(ff)
    path = 'C:\\Users\\cvj98\\Desktop\\Emo\\Excitied\\'+name
    print(path)
    img.convert('L').save(path)

globpath = 'C:\\Users\\cvj98\\Desktop\\Emo\\Happy\\*.jpg'
print(globpath)
images = glob.glob(globpath)

for ff in images :
    name = '_'+ff[33:]
    img = Image.open(ff)
    path = 'C:\\Users\\cvj98\\Desktop\\Emo\\Happy\\'+name
    print(path)
    img.convert('L').save(path)

globpath = 'C:\\Users\\cvj98\\Desktop\\Emo\\Ignore\\*.jpg'
print(globpath)
images = glob.glob(globpath)

for ff in images :
    name = '_'+ff[34:]
    img = Image.open(ff)
    path = 'C:\\Users\\cvj98\\Desktop\\Emo\\Ignore\\'+name
    print(path)
    img.convert('L').save(path)

globpath = 'C:\\Users\\cvj98\\Desktop\\Emo\\Sad\\*.jpg'
print(globpath)
images = glob.glob(globpath)

for ff in images :
    name = '_'+ff[31:]
    img = Image.open(ff)
    path = 'C:\\Users\\cvj98\\Desktop\\Emo\\Sad\\'+name
    print(path)
    img.convert('L').save(path)

globpath = 'C:\\Users\\cvj98\\Desktop\\Emo\\scared\\*.jpg'
print(globpath)
images = glob.glob(globpath)

for ff in images :
    name = '_'+ff[34:]
    img = Image.open(ff)
    path = 'C:\\Users\\cvj98\\Desktop\\Emo\\scared\\'+name
    print(path)
    img.convert('L').save(path)

globpath = 'C:\\Users\\cvj98\\Desktop\\Emo\\sleepy\\*.jpg'
print(globpath)
images = glob.glob(globpath)

for ff in images :
    name = '_'+ff[34:]
    img = Image.open(ff)
    path = 'C:\\Users\\cvj98\\Desktop\\Emo\\sleepy\\'+name
    print(path)
    img.convert('L').save(path)

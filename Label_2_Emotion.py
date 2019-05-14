import cv2
import glob
import os
'''
cvj98을 사용자 계정으로 변경 및 이미지 파일 있는 경로로 수정
'''
images = glob.glob('C:\\Users\\cvj98\\Desktop\\cats\\CAT_01\\*.jpg')

for ff in images:
    img = cv2.imread(ff)

    '''
    name에 들어가는 문자열은 경로에 따라 다르게 또는 뒤에서 부터 슬라이싱
    '''
    name = ff[35:]
    img2 = cv2.resize(img, dsize=(445, 445), interpolation=cv2.INTER_AREA)
    print("this image is : "+name)
    cv2.imshow(name,img2)
    cv2.waitKey(100)
    a = int(input('Afraid : 1\nAngry : 2\nIgnore : 3\nInterested : 4\nExcited : 5\nScared : 6\nNot Used : 7\nEnter the Num : '))
    cv2.destroyAllWindows()
    
    if a==1:
        path ='C:\\Users\\cvj98\\Desktop\\Emotion\\afraid\\'
        cv2.imwrite(os.path.join(path , name), img2)
    if a==2:
        path ='C:\\Users\\cvj98\\Desktop\\Emotion\\Angry\\'
        cv2.imwrite(os.path.join(path , name), img2)
    if a==3:
        path ='C:\\Users\\cvj98\\Desktop\\Emotion\\Ignore\\'
        cv2.imwrite(os.path.join(path , name), img2)
    if a==4:
        path ='C:\\Users\\cvj98\\Desktop\\Emotion\\interested\\'
        cv2.imwrite(os.path.join(path , name), img2)
    if a==5:
        path ='C:\\Users\\cvj98\\Desktop\\Emotion\\Excited\\'
        cv2.imwrite(os.path.join(path , name), img2)
    if a==6:
        path ='C:\\Users\\cvj98\\Desktop\\Emotion\\Scared\\'
        cv2.imwrite(os.path.join(path , name), img2)
    if a==7:
        path ='C:\\Users\\cvj98\\Desktop\\Emotion\\Trash\\'
        cv2.imwrite(os.path.join(path , name), img2)
    
    

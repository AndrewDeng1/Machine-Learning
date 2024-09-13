# Importing necessary libraries
import cv2
import mediapipe as mp
import numpy as np
import time
from tkinter import *
from PIL import Image, ImageTk
from datetime import timedelta
import tkinter.font as font
import socket
from datetime import datetime
import os

stage = None
jjcounter = None
sqcounter = None
hkcounter = None

def display():
    print(f'Stage: {stage}')
    print(f'Jumping Jacks: {jjcounter}')
    print(f'Squats: {sqcounter}')
    print(f'High Knees: {hkcounter}')

# Creating a new tkinter window
win = Tk()
win.title("Jumping Jacks Trainer")    # setting title of window
win.geometry("900x600+200+30")        # setting geometry

# Making a frame within the window
frame_1 = Frame(win, width=900, height=700, bg="#ffdbac").place(x=0, y=0)

# Make a label to insert our video within
w = 450
h = 300
L1 = Label(frame_1, width=w, height=h, relief=SUNKEN)
L1.place(x=200, y=260)

# Initialize MediaPipe Pose and Drawing utilities
mp_drawing = mp.solutions.drawing_utils
mp_pose = mp.solutions.pose

# Function to calculate angle
def calculate_angle(a, b, c):
    a = np.array(a)  # First
    b = np.array(b)  # Mid
    c = np.array(c)  # End

    radians = np.arctan2(c[1] - b[1], c[0] - b[0]) - np.arctan2(a[1] - b[1], a[0] - b[0])
    angle = np.abs(radians * 180.0 / np.pi)

    if angle > 180.0:
        angle = 360 - angle

    return angle

# REPS counter variables
jjcounter = 0  # counter for jumping jacks
sqcounter = 0  # counter for squats
hkcounter = 0  # counter for high knees
stage = None
pTime = 0

# Start calculating time for displaying
start = time.time()

cap = cv2.VideoCapture(0)

with mp_pose.Pose(min_detection_confidence=0.5, min_tracking_confidence=0.5) as pose:

    while cap.isOpened():
        image = cap.read()[1]  # reading images from video
        image = cv2.resize(image, (w, h))  # resizing our video according to the label

        # Recolor image to RGB
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        image.flags.writeable = False

        # Make detection
        results = pose.process(image)

        # Recolor back to BGR
        image.flags.writeable = True  # Make image writable
        image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)

        # Extract Landmarks
        try:
            landmarks = results.pose_landmarks.landmark

            # Get coordinates
            shoulder1 = [landmarks[mp_pose.PoseLandmark.LEFT_SHOULDER.value].x, landmarks[mp_pose.PoseLandmark.LEFT_SHOULDER.value].y]
            hip1 = [landmarks[mp_pose.PoseLandmark.LEFT_HIP.value].x, landmarks[mp_pose.PoseLandmark.LEFT_HIP.value].y]
            wrist1 = [landmarks[mp_pose.PoseLandmark.LEFT_WRIST.value].x, landmarks[mp_pose.PoseLandmark.LEFT_WRIST.value].y]
            ankle1 = [landmarks[mp_pose.PoseLandmark.LEFT_ANKLE.value].x, landmarks[mp_pose.PoseLandmark.LEFT_ANKLE.value].y]
            knee1 = [landmarks[mp_pose.PoseLandmark.LEFT_KNEE.value].x, landmarks[mp_pose.PoseLandmark.LEFT_KNEE.value].y]
            elbow1 = [landmarks[mp_pose.PoseLandmark.LEFT_ELBOW.value].x, landmarks[mp_pose.PoseLandmark.LEFT_ELBOW.value].y]
            index1 = [landmarks[mp_pose.PoseLandmark.LEFT_INDEX.value].x, landmarks[mp_pose.PoseLandmark.LEFT_INDEX.value].y]

            shoulder2 = [landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value].x, landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value].y]
            hip2 = [landmarks[mp_pose.PoseLandmark.RIGHT_HIP.value].x, landmarks[mp_pose.PoseLandmark.RIGHT_HIP.value].y]
            wrist2 = [landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].x, landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].y]
            ankle2 = [landmarks[mp_pose.PoseLandmark.RIGHT_ANKLE.value].x, landmarks[mp_pose.PoseLandmark.RIGHT_ANKLE.value].y]
            knee2 = [landmarks[mp_pose.PoseLandmark.RIGHT_KNEE.value].x, landmarks[mp_pose.PoseLandmark.RIGHT_KNEE.value].y]
            elbow2 = [landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value].x, landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value].y]

            # Calculate angles
            hip_shoulder_wrist1 = calculate_angle(hip1, shoulder1, wrist1)
            hip_shoulder_wrist2 = calculate_angle(hip2, shoulder2, wrist2)
            shoulder_hip_ankle1 = calculate_angle(shoulder1, hip1, ankle1)
            shoulder_hip_ankle2 = calculate_angle(shoulder2, hip2, ankle2)

            hip_knee_ankle1 = calculate_angle(hip1, knee1, ankle1)
            hip_knee_ankle2 = calculate_angle(hip2, knee2, ankle2)
            shoulder_hip_knee1 = calculate_angle(shoulder1, hip1, knee1)
            shoulder_hip_knee2 = calculate_angle(shoulder2, hip2, knee2)

            shoulder_ankle_wrist1 = calculate_angle(shoulder1, ankle1, wrist1)
            shoulder_ankle_wrist2 = calculate_angle(shoulder2, ankle2, wrist2)
            shoulder_elbow_wrist1 = calculate_angle(shoulder1, elbow1, wrist1)
            shoulder_elbow_wrist2 = calculate_angle(shoulder2, elbow2, wrist2)

            # Visualize angle
            cv2.putText(image, str(int(hip_shoulder_wrist1)),
                        tuple(np.multiply(shoulder1, [300, 480]).astype(int)),
                        cv2.FONT_HERSHEY_COMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)

            cv2.putText(image, str(int(hip_shoulder_wrist2)),
                        tuple(np.multiply(shoulder2, [600, 480]).astype(int)),
                        cv2.FONT_HERSHEY_COMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)

            cv2.putText(image, str(int(shoulder_hip_ankle1)),
                        tuple(np.multiply(hip1, [300, 480]).astype(int)),
                        cv2.FONT_HERSHEY_COMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)

            cv2.putText(image, str(int(shoulder_hip_ankle2)),
                        tuple(np.multiply(hip2, [600, 480]).astype(int)),
                        cv2.FONT_HERSHEY_COMPLEX, 0.5, (0, 0, 0), 2, cv2.LINE_AA)

            # Detect and count jumping jacks, squats, and high knees
            if hip_shoulder_wrist1 < 30 and hip_shoulder_wrist2 < 30 and shoulder_hip_ankle1 > 170 and shoulder_hip_ankle2 > 170:
                if stage == 'jjup1':
                    jjcounter += 1
                    stage = 'jjdown1'
                    display()

            if hip_shoulder_wrist1 < 30 and hip_shoulder_wrist2 < 30 and shoulder_hip_ankle1 < 170 and shoulder_hip_ankle2 < 170:
                if stage == 'jjup2':
                    jjcounter += 1
                    stage = 'jjdown2'
                    display()

            if hip_shoulder_wrist1 > 150 and hip_shoulder_wrist2 > 150 and shoulder_hip_ankle1 < 170 and shoulder_hip_ankle2 < 170 and stage == 'jjdown1':
                stage = 'jjup1'
                display()

            if hip_shoulder_wrist1 > 150 and hip_shoulder_wrist2 > 150 and shoulder_hip_ankle1 > 170 and shoulder_hip_ankle2 > 170 and stage == 'jjdown2':
                stage = 'jjup2'
                display()

            if hip_knee_ankle1 <= 130 and hip_knee_ankle2 <= 130:
                stage = 'sqdown'
                display()

            if hip_knee_ankle1 >= 110 and hip_knee_ankle2 >= 110 and stage == 'sqdown':
                stage = 'squp'
                sqcounter += 1
                display()

            if shoulder_hip_knee1 >= 150 and hip_knee_ankle1 >= 150 and shoulder_hip_knee2 >= 150 and hip_knee_ankle2 >= 150:
                stage = 'hkleft'
                display()

            if shoulder_hip_knee1 <= 130 and hip_knee_ankle1 <= 110 and shoulder_hip_knee2 >= 150 and hip_knee_ankle2 >= 150:
                if stage == 'hkright':
                    hkcounter += 1
                stage = 'hkleft'
                display()

            if shoulder_hip_knee1 >= 150 and hip_knee_ankle1 >= 150 and shoulder_hip_knee2 <= 130 and hip_knee_ankle2 <= 110:
                if stage == 'hkleft':
                    hkcounter += 1
                stage = 'hkright'
                display()
            

        except:
            pass

        # Render detections
        mp_drawing.draw_landmarks(image, results.pose_landmarks, mp_pose.POSE_CONNECTIONS,
                                  mp_drawing.DrawingSpec(color=(255, 0, 0), thickness=2, circle_radius=2),
                                  mp_drawing.DrawingSpec(color=(0, 255, 0), thickness=2, circle_radius=2))

        # Add counts to the image
        cv2.putText(image, 'Jumping Jacks Counter: ' + str(jjcounter),
                    (20, 50),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 0, 0), 2, cv2.LINE_AA)

        cv2.putText(image, 'Squats Counter: ' + str(sqcounter),
                    (20, 100),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 0, 0), 2, cv2.LINE_AA)

        cv2.putText(image, 'High Knees Counter: ' + str(hkcounter),
                    (20, 150),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 0, 0), 2, cv2.LINE_AA)

        # Update FPS
        cTime = time.time()
        fps = 1 / (cTime - pTime)
        pTime = cTime

        cv2.putText(image, 'FPS: ' + str(int(fps)),
                    (20, 200),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 0, 0), 2, cv2.LINE_AA)

        # Convert image to PIL format and then to ImageTk format
        image = Image.fromarray(image)
        imgtk = ImageTk.PhotoImage(image=image)
        L1.imgtk = imgtk
        L1.configure(image=imgtk)

        # Update the window
        win.update()

    cap.release()
    cv2.destroyAllWindows()

from tkinter import *
from tkinter.messagebox import *
import tkinter.filedialog as filedialog
import tkinter as tk
from tkinter import ttk
from LL1 import *
window = Tk()
window.title('Part(2)')
lbl = tk.Label(text='Welcome :)')
window.config(background='lightblue')
lbl.pack()

n2= Label(text = "Enter your input please ")
n2.pack(fill=X)

topFrame = Frame(window)
topFrame.pack()

no=Entry(window,width=60)
no.pack()

def button1():  
    parser = LL1Parser()
    value=no.get()
    parser.Parse(value)
    
bt2=Button(window,text='Check the Input',width='12',command=button1)
bt2.place(x=150,y=300)
bt2.pack()

def buClick():
    print(no.get())
    no.delete(0,END)

window.mainloop()

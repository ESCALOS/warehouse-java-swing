/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nanoka.almacenrepuestos;

import com.nanoka.almacenrepuestos.frames.Login;

/**
 *
 * @author carlo
 */
public class AlmacenRepuestos {
    public static void main(String[] args) {
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}

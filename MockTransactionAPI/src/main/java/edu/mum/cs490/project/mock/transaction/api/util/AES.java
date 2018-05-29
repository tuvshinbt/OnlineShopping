/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.mock.transaction.api.util;

/**
 *
 * @author tuvshuu
 */
public interface AES {

    public String encrypt(String strToEncrypt);

    public String decrypt(String strToDecrypt);

    public String getSecretKeyWord();
}

package com.zyj.disk.sys.tool.encryption.rsa;

import lombok.AllArgsConstructor;

/**
 * RSA set
 */
@AllArgsConstructor
public enum RsaSet {
    PUBLIC(new RSA(2048, 65537)),
    RESPONSE(new RSA(2048, 65537));

    public final RSA RSA;
}
package com.human.project.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class PasswordGenerator {
    private SecureRandom random = new SecureRandom();

    public String nextPassword() {
        return new BigInteger(130, random).toString(16);
    }
}

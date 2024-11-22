package com.myproject.busticket.validations;

public class CheckpointValidation {
    public static String validateCheckpointFields(String placeName, String address, String province, String city,
            String phone, String region) {
        if (placeName == null || placeName.isEmpty() ||
                address == null || address.isEmpty() ||
                province == null || province.isEmpty() ||
                city == null || city.isEmpty() ||
                phone == null || phone.isEmpty() ||
                region == null || region.isEmpty()) {
            return "All fields are required.";
        }

        String regex = "^[A-Za-z0-9\\s\\*\\,\\.\\-\\_\\(\\)\\[\\]\\{\\}\\@\\#\\$\\%\\^\\&\\!\\?\\+\\=\\:\\;\\'\\\"ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸỳỵỷỹ]*$";
        if (!placeName.matches(regex) || !address.matches(regex) || !province.matches(regex) ||
                !city.matches(regex) || !phone.matches(regex) || !region.matches(regex)) {
            return "Invalid characters in input fields.";
        }

        // Check for valid phone number:
        if (!phone.matches("^[0-9\\.]*$")) {
            return "Invalid phone number.";
        }

        return null;
    }
}

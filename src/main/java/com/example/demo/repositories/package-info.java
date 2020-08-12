@TypeDefs(
        {
                @TypeDef(
                        name = "localDateTime",
                        defaultForType = LocalDateTime.class,
                        typeClass = PersistentLocalDateTime.class),

                @TypeDef(
                        name = "localDate",
                        defaultForType = LocalDate.class,
                        typeClass = PersistentLocalDate.class)


        })
package com.example.demo.repositories;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDate;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
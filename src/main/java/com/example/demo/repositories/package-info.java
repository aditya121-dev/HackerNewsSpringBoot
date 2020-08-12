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
/***
 * @author Aditya Soni( adityasoni182@gmail.com )
 * @version v1
 * @since 12 August 2020
 */
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDate;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
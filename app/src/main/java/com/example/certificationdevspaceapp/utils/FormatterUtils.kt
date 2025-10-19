package com.example.certificationdevspaceapp.utils

object FormatterUtils {

     fun formatPopulation(pop: Long): String {
        return when {
            pop >= 1_000_000_000 -> String.format("%.1f B", pop / 1_000_000_000.0)
            pop >= 1_000_000 -> String.format("%.1f M", pop / 1_000_000.0)
            pop >= 1_000 -> String.format("%.1f K", pop / 1_000.0)
            else -> pop.toString()
        }
    }

}
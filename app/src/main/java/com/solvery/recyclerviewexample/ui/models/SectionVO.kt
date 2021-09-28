package com.solvery.recyclerviewexample.ui.models

class SectionVO(
    val name: String
) : VisualObject {
    override val id: String
        get() = name
}
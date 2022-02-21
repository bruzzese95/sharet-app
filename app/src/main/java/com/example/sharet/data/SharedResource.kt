package com.example.sharet.data

class SharedResource(val name: String) {

    companion object {
        fun createResourcesList(resourceName: String) : ArrayList<SharedResource> {
            val resources = ArrayList<SharedResource>()
            resources.add(SharedResource(name = resourceName))
            return resources
        }
    }
}